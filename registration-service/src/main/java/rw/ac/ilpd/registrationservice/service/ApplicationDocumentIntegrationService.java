package rw.ac.ilpd.registrationservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentClient;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.registrationservice.mapper.ApplicationSponsorMapper;
import rw.ac.ilpd.registrationservice.mapper.DocumentMapper;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.registrationservice.repository.ApplicationSponsorRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ApplicationDocumentIntegrationService that uses the notification service's document API
 * instead of directly using DocumentEntity and DocumentType.
 */
@Service
@Transactional
public class ApplicationDocumentIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationDocumentIntegrationService.class);
    
    private final NotificationDocumentClient documentClient;
    private final NotificationDocumentTypeClient documentTypeClient;
    private final ApplicationService applicationService;
    private final DocumentMapper documentMapper;
    private final ApplicationSponsorRepository sponsorRepository;
    private final ApplicationSponsorMapper sponsorMapper;

    @Autowired
    public ApplicationDocumentIntegrationService(
            NotificationDocumentClient documentClient,
            NotificationDocumentTypeClient documentTypeClient,
            ApplicationService applicationService,
            DocumentMapper documentMapper,
            ApplicationSponsorRepository sponsorRepository,
            ApplicationSponsorMapper sponsorMapper) {
        this.documentClient = documentClient;
        this.documentTypeClient = documentTypeClient;
        this.applicationService = applicationService;
        this.documentMapper = documentMapper;
        this.sponsorRepository = sponsorRepository;
        this.sponsorMapper = sponsorMapper;
    }

    /**
     * Upload a document and attach it to an application in one step
     */
    public ApplicationDocumentSubmissionResponse uploadAndAttachDocument(String applicationId,
                                                                         MultipartFile file,
                                                                         String requiredDocNameId,
                                                                         String documentTypeId,
                                                                         String uploadedBy) {
        log.debug("Uploading and attaching document to application: {}", applicationId);

        // Determine storage path based on required document type
        String objectPath = determineObjectPath(requiredDocNameId, applicationId);
        String bucketName = ObjectUploadPath.StudentApplication.BASE;

        // Store the document using notification service - UPDATED CALL
        ResponseEntity<DocumentResponse> response = documentClient.uploadSingleObject(
            file,
            bucketName,
            objectPath
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to upload document to notification service");
        }

        DocumentResponse document = response.getBody();

        // Create application document submission
        ApplicationDocumentSubmissionRequest submissionRequest = new ApplicationDocumentSubmissionRequest();
        submissionRequest.setDocumentId(document.getId());
        submissionRequest.setRequiredDocNameId(requiredDocNameId);

        // Attach to application
        return applicationService.addDocumentToApplication(applicationId, submissionRequest);
    }
    /**
     * Upload multiple documents and attach them to an application
     */
    public List<ApplicationDocumentSubmissionResponse> uploadAndAttachMultipleDocuments(String applicationId,
                                                                                        List<MultipartFile> files,
                                                                                        List<String> requiredDocNameIds,
                                                                                        String documentTypeId,
                                                                                        String uploadedBy) {
        log.debug("Uploading and attaching multiple documents to application: {}", applicationId);

        List<ApplicationDocumentSubmissionResponse> responses = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String requiredDocNameId = i < requiredDocNameIds.size() ? requiredDocNameIds.get(i) : "others";

            ApplicationDocumentSubmissionResponse response = uploadAndAttachDocument(
                    applicationId, file, requiredDocNameId, documentTypeId, uploadedBy);
            responses.add(response);
        }

        return responses;
    }

    /**
     * Get application documents with rich details including download URLs
     */
    @Transactional(readOnly = true)
    public List<ApplicationDocumentDetailResponse> getApplicationDocumentsWithDetails(String applicationId) {
        log.debug("Getting application documents with details for application: {}", applicationId);

        List<ApplicationDocumentSubmissionResponse> submissions =
                applicationService.getApplicationDocuments(applicationId);

        List<ApplicationDocumentDetailResponse> detailedResponses = new ArrayList<>();

        for (ApplicationDocumentSubmissionResponse submission : submissions) {
            try {
                // Get document from notification service
                ResponseEntity<DocumentResponse> documentResponse = documentClient.findDocument(submission.getDocumentId());
                
                if (!documentResponse.getStatusCode().is2xxSuccessful() || documentResponse.getBody() == null) {
                    log.warn("Failed to retrieve document: {}", submission.getDocumentId());
                    continue;
                }
                
                DocumentResponse document = documentResponse.getBody();
                
                // Get document type from notification service
                ResponseEntity<DocumentTypeResponse> documentTypeResponse = documentTypeClient.findDocumentType(document.getTypeId());
                DocumentTypeResponse documentType = documentTypeResponse.getStatusCode().is2xxSuccessful() && documentTypeResponse.getBody() != null
                        ? documentTypeResponse.getBody()
                        : null;

                ApplicationDocumentDetailResponse detailResponse = new ApplicationDocumentDetailResponse();
                detailResponse.setSubmission(submission);
                detailResponse.setDocument(document);
                detailResponse.setDocumentType(documentType);
                detailResponse.setDownloadUrl(document.getUrl());
                detailResponse.setFileName(null); // DocumentResponse doesn't have originalName
                detailResponse.setFileSize(0); // DocumentResponse doesn't have fileSize
                detailResponse.setContentType(null); // DocumentResponse doesn't have contentType

                detailedResponses.add(detailResponse);
            } catch (Exception e) {
                log.error("Error retrieving document details for document: {}", submission.getDocumentId(), e);
            }
        }

        return detailedResponses;
    }

    /**
     * Upload a recommendation letter for a sponsor
     */
    public ApplicationSponsorResponse uploadRecommendationForSponsor(
            String applicationId, String sponsorId, MultipartFile file, String uploadedBy) {
        log.debug("Uploading recommendation letter for sponsor: {} of application: {}", sponsorId, applicationId);

        // Find existing sponsor relationship
        ApplicationSponsor sponsor = sponsorRepository
                .findByApplicationIdAndSponsorId(applicationId, sponsorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sponsor relationship not found for application: " + applicationId + " and sponsor: " + sponsorId));

        // Upload recommendation letter using notification service
        String bucketName = ObjectUploadPath.StudentApplication.BASE;
        String objectPath = ObjectUploadPath.StudentApplication.OTHERS + "/" + applicationId + "/recommendations";

        // Upload document using notification service - UPDATED CALL
        ResponseEntity<DocumentResponse> response = documentClient.uploadSingleObject(
            file,
            bucketName,
            objectPath
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to upload recommendation letter to notification service");
        }

        DocumentResponse document = response.getBody();

        // Update sponsor with recommendation letter ID
        sponsor.setRecommendationLetterId(document.getId());
        ApplicationSponsor updatedSponsor = sponsorRepository.save(sponsor);

        return sponsorMapper.toResponse(updatedSponsor);
    }
    /**
     * Determine upload path based on required document name
     */
    private String determineObjectPath(String requiredDocNameId, String applicationId) {
        return switch (requiredDocNameId.toLowerCase()) {
            case "degree" -> ObjectUploadPath.StudentApplication.DEGREE + "/" + applicationId;
            case "transcript" -> ObjectUploadPath.StudentApplication.TRANSCRIPT + "/" + applicationId;
            case "identification", "id" -> ObjectUploadPath.StudentApplication.IDENTIFICATION + "/" + applicationId;
            case "profile_picture", "photo" ->
                    ObjectUploadPath.StudentApplication.PROFILE_PICTURE + "/" + applicationId;
            case "application_fees", "fees" -> ObjectUploadPath.StudentApplication.FEES + "/" + applicationId;
            default -> ObjectUploadPath.StudentApplication.OTHERS + "/" + applicationId;
        };
    }
}