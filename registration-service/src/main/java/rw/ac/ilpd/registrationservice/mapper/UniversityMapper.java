package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A mapper component responsible for transforming data between the University entity, request,
 * and response representations.
 * <p>
 * This class provides utility methods to perform the following conversions:
 * 1. Convert a {@code UniversityRequest} into a {@code University} entity for database operations.
 * 2. Convert a {@code University} entity into a {@code UniversityResponse} object for API responses.
 * 3. Convert a list of {@code University} entities into a list of {@code UniversityResponse} objects.
 * 4. Update an existing {@code University} entity with data from a {@code UniversityRequest}.
 */
@Component
public class UniversityMapper {

    /**
     * Converts a {@code UniversityRequest} object into a {@code University} entity.
     *
     * @param request an instance of {@code UniversityRequest} containing the university details
     *                such as name and country
     * @return a new instance of {@code University} with the details provided in the request.
     * The ID field will be null and generated at a later stage.
     */
    public University toEntity(UniversityRequest request) {
        return new University(null, // ID will be generated
                request.getName(), request.getCountry());
    }

    /**
     * Converts a {@code University} entity into a {@code UniversityResponse} object.
     * <p>
     * This method maps the fields of the {@code University} entity, such as ID, name,
     * and country, to their corresponding fields in the {@code UniversityResponse} object.
     * Additionally, it sets the {@code createdAt} field to the current date and time.
     *
     * @param entity the {@code University} entity to be transformed into a {@code UniversityResponse} object
     * @return a {@code UniversityResponse} object containing the details of the given {@code University} entity
     */
    public UniversityResponse toResponse(University entity) {
        return UniversityResponse.builder().id(entity.getId()).name(entity.getName()).country(entity.getCountry())
                .createdAt(LocalDateTime.now()) // Add if entity has createdAt field
                .build();
    }

    /**
     * Converts a list of {@code University} entities into a list of {@code UniversityResponse} objects.
     *
     * @param entities the list of {@code University} entities to be transformed
     * @return a list of {@code UniversityResponse} objects representing the transformed data
     */
    public List<UniversityResponse> toResponseList(List<University> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    /**
     * Updates an existing {@code University} entity with new data from a {@code UniversityRequest}.
     *
     * @param existing the existing {@code University} entity to be updated
     * @param request  the {@code UniversityRequest} containing the new data to update the entity
     * @return the updated {@code University} entity
     */
    public University updateEntity(University existing, UniversityRequest request) {
        existing.setName(request.getName());
        existing.setCountry(request.getCountry());
        return existing;
    }
}