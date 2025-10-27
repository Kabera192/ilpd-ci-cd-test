package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ApplicationSponsorMapper class is responsible for mapping between different representations
 * of ApplicationSponsor objects. It provides methods to transform ApplicationSponsorRequest to
 * ApplicationSponsor entities, entities to ApplicationSponsorResponse DTOs, and to update existing
 * entities with new data.
 * <p>
 * This class is typically used in the context of managing application sponsors in the ILPD
 * application processing system, facilitating the creation, retrieval, and modification of sponsor
 * records.
 * <p>
 * Key Responsibilities:
 * - Convert ApplicationSponsorRequest objects to ApplicationSponsor entities.
 * - Convert ApplicationSponsor entities to ApplicationSponsorResponse objects.
 * - Convert lists of ApplicationSponsor entities to lists of ApplicationSponsorResponse objects.
 * - Update existing ApplicationSponsor entities with new data from ApplicationSponsorRequest objects.
 */
@Component
public class ApplicationSponsorMapper {

    /**
     * Maps an ApplicationSponsorRequest object to an ApplicationSponsor entity object.
     *
     * @param request the ApplicationSponsorRequest containing the data to map
     * @return an ApplicationSponsor entity populated with the data from the provided request
     */
    public ApplicationSponsor toEntity(ApplicationSponsorRequest request) {
        ApplicationSponsor sponsor = new ApplicationSponsor();
        sponsor.setSponsorId(request.getSponsorId());
        sponsor.setApplicationId(request.getApplicationId());
        sponsor.setRecommendationLetterId(request.getRecommendationLetterDocId());
        return sponsor;
    }

    /**
     * Converts an ApplicationSponsor entity into an ApplicationSponsorResponse DTO.
     *
     * @param entity the ApplicationSponsor entity to be converted
     * @return an ApplicationSponsorResponse object containing the data mapped from the entity
     */
    public ApplicationSponsorResponse toResponse(ApplicationSponsor entity) {
        return ApplicationSponsorResponse.builder().id(entity.getId()).sponsorId(entity.getSponsorId())
                // Don't set applicationId to avoid redundancy - parent ApplicationResponse already has the application ID
                .recommendationLetterDocId(entity.getRecommendationLetterId())
                .createdAt(LocalDateTime.now()) // Add if entity has createdAt field
                .build();
    }

    /**
     * Converts a list of ApplicationSponsor entities into a list of ApplicationSponsorResponse DTOs.
     *
     * @param entities the list of ApplicationSponsor entities to be transformed
     * @return a list of ApplicationSponsorResponse DTOs representing the given entities
     */
    public List<ApplicationSponsorResponse> toResponseList(List<ApplicationSponsor> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
    
    /**
     * Converts a list of ApplicationSponsor entities into a list of ApplicationSponsorResponse DTOs
     * with the specified applicationId set in each response.
     *
     * @param entities     the list of ApplicationSponsor entities to be transformed
     * @param applicationId the ID of the application to set in each response
     * @return a list of ApplicationSponsorResponse DTOs with the applicationId set
     */
    public List<ApplicationSponsorResponse> toResponseListWithContext(List<ApplicationSponsor> entities, String applicationId) {
        return entities.stream()
                .map(entity -> toResponseWithContext(entity, applicationId))
                .collect(Collectors.toList());
    }
    
    /**
     * Converts an ApplicationSponsor entity into an ApplicationSponsorResponse DTO
     * with the specified applicationId set in the response.
     *
     * @param entity       the ApplicationSponsor entity to be converted
     * @param applicationId the ID of the application to set in the response
     * @return an ApplicationSponsorResponse object with the applicationId set
     */
    public ApplicationSponsorResponse toResponseWithContext(ApplicationSponsor entity, String applicationId) {
        ApplicationSponsorResponse response = toResponse(entity);
        response.setApplicationId(applicationId);
        return response;
    }

    /**
     * Updates an existing ApplicationSponsor entity with the data provided in the ApplicationSponsorRequest object.
     * This method replaces the sponsor ID, application ID, and recommendation letter document ID of the existing entity
     * with the corresponding values from the request.
     *
     * @param existing the existing ApplicationSponsor entity to be updated
     * @param request  the ApplicationSponsorRequest object containing new data to update the entity
     * @return the updated ApplicationSponsor entity
     */
    public ApplicationSponsor updateEntity(ApplicationSponsor existing, ApplicationSponsorRequest request) {
        existing.setSponsorId(request.getSponsorId());
        existing.setApplicationId(request.getApplicationId());
        existing.setRecommendationLetterId(request.getRecommendationLetterDocId());
        return existing;
    }
}
