package rw.ac.ilpd.registrationservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.util.Optional;

/**
 * This class was previously used to initialize default document types on application startup
 * by delegating to the notification service. It has been disabled as this functionality
 * is now handled directly by the notification service.
 * 
 * The code is kept for reference purposes only and is no longer active in the application.
 */
public class DocumentDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DocumentDataInitializer.class);
    private final NotificationDocumentTypeClient documentTypeClient;
    
    private boolean initializationEnabled = false;

    // Constructor is kept for reference but the class is no longer instantiated by Spring
    public DocumentDataInitializer(NotificationDocumentTypeClient documentTypeClient) {
        this.documentTypeClient = documentTypeClient;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initializationEnabled) {
            log.info("Document type initialization is enabled, proceeding with initialization");
            initializeDocumentTypes();
        } else {
            log.info("Document type initialization is disabled, skipping initialization");
        }
    }

    private void initializeDocumentTypes() {
        log.info("Initializing document types using notification service");
        
        // Student Application Document Types
        createDocumentTypeIfNotExists("Academic Degree", ObjectUploadPath.StudentApplication.DEGREE);
        createDocumentTypeIfNotExists("Academic Transcript", ObjectUploadPath.StudentApplication.TRANSCRIPT);
        createDocumentTypeIfNotExists("Identification", ObjectUploadPath.StudentApplication.IDENTIFICATION);
        createDocumentTypeIfNotExists("Profile Picture", ObjectUploadPath.StudentApplication.PROFILE_PICTURE);
        createDocumentTypeIfNotExists("Application Fees", ObjectUploadPath.StudentApplication.FEES);
        createDocumentTypeIfNotExists("Other Documents", ObjectUploadPath.StudentApplication.OTHERS);

        // Course Document Types
        createDocumentTypeIfNotExists("Course Material", ObjectUploadPath.Course.MATERIAL);

        // Finance Document Types
        createDocumentTypeIfNotExists("Finance Attachment", ObjectUploadPath.Finance.ATTACHMENT);

        // User Document Types
        createDocumentTypeIfNotExists("User Profile", ObjectUploadPath.User.PROFILE);

        // General Template Type
        createDocumentTypeIfNotExists("Template", "templates");

        createDocumentTypeIfNotExists("Recommendation Letter", ObjectUploadPath.StudentApplication.OTHERS + "/recommendations");
        
        log.info("Document types initialization completed");
    }

    private void createDocumentTypeIfNotExists(String name, String path) {
        log.debug("Checking if document type exists: {}", name);
        
        // Check if document type exists by name
        Optional<DocumentTypeResponse> existing = findDocumentTypeByName(name);
        
        if (existing.isEmpty()) {
            log.info("Creating document type: {}", name);
            
            DocumentTypeRequest request = new DocumentTypeRequest();
            request.setName(name);
            request.setPath(path);
            
            try {
                ResponseEntity<DocumentTypeResponse> response = documentTypeClient.createDocumentType(request);
                
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    log.info("Document type created successfully: {}", response.getBody().getId());
                } else {
                    log.error("Failed to create document type: {}", name);
                }
            } catch (Exception e) {
                log.error("Error creating document type: {}", name, e);
            }
        } else {
            log.debug("Document type already exists: {}", name);
        }
    }
    
    private Optional<DocumentTypeResponse> findDocumentTypeByName(String name) {
        try {
            PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 100, "name", name);
            
            if (response != null && response.getContent() != null) {
                return response.getContent().stream()
                        .filter(dt -> dt.getName().equals(name))
                        .findFirst();
            }
        } catch (Exception e) {
            log.error("Error finding document type by name: {}", name, e);
        }
        
        return Optional.empty();
    }
}