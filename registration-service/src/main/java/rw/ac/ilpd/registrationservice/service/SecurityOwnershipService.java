package rw.ac.ilpd.registrationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.registrationservice.repository.AcademicBackgroundRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationSponsorRepository;

/**
 * Service to handle ownership checks for security authorization.
 * This service is used by @PreAuthorize annotations in controllers to determine
 * if the current user owns or has access to specific resources.
 */
@Service
public class SecurityOwnershipService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityOwnershipService.class);

    @Autowired
    private ApplicationRepository applicationRepository;


    @Autowired
    private ApplicationSponsorRepository applicationSponsorRepository;

    /**
     * Checks if the current user is the owner of the specified application.
     *
     * @param applicationId the ID of the application
     * @param currentUserId the current authenticated user's ID
     * @return true if the user owns the application, false otherwise
     */
    public boolean isApplicationOwner(String applicationId, String currentUserId) {
        try {
            if (applicationId == null || currentUserId == null) {
                return false;
            }

            applicationRepository.findById(applicationId)
                    .map(application -> false);
            return false;
        } catch (Exception e) {
            logger.error("Error checking application ownership for applicationId: {} and userId: {}", applicationId,
                    currentUserId, e);
            return false;
        }
    }

    /**
     * Checks if the current user owns the application associated with the specified document.
     *
     * @param documentId    the ID of the document
     * @param currentUserId the current authenticated user's ID
     * @return true if the user owns the application containing the document, false otherwise
     */
    public boolean isDocumentOwner(String documentId, String currentUserId) {
        try {
            if (documentId == null || currentUserId == null) {
                return false;
            }

            // Find applications that contain this document
            return applicationRepository.findByApplicationDocumentsContaining(documentId).stream()
                    .anyMatch(app -> currentUserId.equals(app.getUserId().toString()));
        } catch (Exception e) {
            logger.error("Error checking document ownership for documentId: {} and userId: {}", documentId,
                    currentUserId, e);
            return false;
        }
    }



    /**
     * Checks if the current user owns the application sponsor record.
     *
     * @param applicationSponsorId the ID of the application sponsor
     * @param currentUserId        the current authenticated user's ID
     * @return true if the user owns the application sponsor, false otherwise
     */
    public boolean isApplicationSponsorOwner(String applicationSponsorId, String currentUserId) {
        try {
            if (applicationSponsorId == null || currentUserId == null) {
                return false;
            }

            applicationSponsorRepository.findById(applicationSponsorId).flatMap(
                            applicationSponsor -> applicationRepository.findById(applicationSponsor.getApplicationId()))
                    .map(application -> false);
            return false;
        } catch (Exception e) {
            logger.error("Error checking application sponsor ownership for applicationSponsorId: {} and userId: {}",
                    applicationSponsorId, currentUserId, e);
            return false;
        }
    }

    /**
     * Checks if the current user has access to view the specified resource.
     * This is a more general method that can be used for additional access control logic.
     *
     * @param resourceId    the ID of the resource
     * @param resourceType  the type of resource (e.g., "application", "academic_background")
     * @param currentUserId the current authenticated user's ID
     * @return true if the user has access, false otherwise
     */
    public boolean hasResourceAccess(String resourceId, String resourceType, String currentUserId) {
        try {
            if (resourceId == null || resourceType == null || currentUserId == null) {
                return false;
            }

            return switch (resourceType.toLowerCase()) {
                case "application" -> isApplicationOwner(resourceId, currentUserId);
                case "application_sponsor" -> isApplicationSponsorOwner(resourceId, currentUserId);
                default -> {
                    logger.warn("Unknown resource type: {}", resourceType);
                    yield false;
                }
            };
        } catch (Exception e) {
            logger.error("Error checking resource access for resourceId: {}, resourceType: {}, userId: {}", resourceId,
                    resourceType, currentUserId, e);
            return false;
        }
    }
}