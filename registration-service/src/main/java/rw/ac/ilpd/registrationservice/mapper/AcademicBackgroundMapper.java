package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.AcademicBackground;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.EmbeddedRecommender;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.RecommenderResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * AcademicBackgroundMapper for handling mapping operations between AcademicBackground entities and DTOs.
 * Designed for user-centric architecture with embedded recommender data.
 */
@Component
public class AcademicBackgroundMapper {

    private final UniversityMapper universityMapper;

    @Autowired
    public AcademicBackgroundMapper(UniversityMapper universityMapper) {
        this.universityMapper = universityMapper;
    }

    /**
     * Converts an AcademicBackgroundRequest to an AcademicBackground entity.
     * Uses userId and embedded recommenders.
     */
    public AcademicBackground toEntity(AcademicBackgroundRequest request, University university,
                                       List<EmbeddedRecommender> recommenders) {
        return new AcademicBackground(
                null, // ID will be generated
                UUID.fromString(request.getUserId()),
                university,
                recommenders != null ? recommenders : Collections.emptyList(),
                request.getDegree(),
                request.getStartDate(),
                request.getEndDate()
        );
    }

    /**
     * Converts an AcademicBackground entity into an AcademicBackgroundResponse DTO.
     * Returns userId and embedded recommender data.
     */
    public AcademicBackgroundResponse toResponse(AcademicBackground entity) {
        if (entity == null) return null;

        List<RecommenderResponse> recommenderResponses = entity.getRecommenders() != null
                ? entity.getRecommenders().stream()
                .map(this::mapEmbeddedRecommenderToResponse)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return AcademicBackgroundResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId().toString())
                .degree(entity.getDegree())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .university(universityMapper.toResponse(entity.getUniversityId()))
                .recommenders(recommenderResponses)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Converts a list of AcademicBackground entities to a list of AcademicBackgroundResponse objects.
     */
    public List<AcademicBackgroundResponse> toResponseList(List<AcademicBackground> entities) {
        if (entities == null) return Collections.emptyList();

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates the fields of an existing AcademicBackground entity with the provided data.
     * Handles userId and embedded recommenders.
     */
    public AcademicBackground updateEntity(AcademicBackground existing, AcademicBackgroundRequest request,
                                           University university, List<EmbeddedRecommender> recommenders) {
        if (existing == null) return null;

        existing.setUserId(UUID.fromString(request.getUserId()));
        existing.setUniversityId(university);
        existing.setRecommenders(recommenders != null ? recommenders : Collections.emptyList());
        existing.setDegree(request.getDegree());
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());
        // updatedAt is automatically set in the setter

        return existing;
    }

    /**
     * Maps an EmbeddedRecommender to a RecommenderResponse.
     */
    private RecommenderResponse mapEmbeddedRecommenderToResponse(EmbeddedRecommender recommender) {
        if (recommender == null) return null;

        return RecommenderResponse.builder()
                .id(recommender.getId())
                .firstName(recommender.getFirstName())
                .lastName(recommender.getLastName())
                .phoneNumber(recommender.getPhoneNumber())
                .email(recommender.getEmail())
                .createdAt(recommender.getCreatedAt())
                .build();
    }

    /**
     * Helper method to convert recommender request to embedded recommender.
     */
    public EmbeddedRecommender toEmbeddedRecommender(rw.ac.ilpd.sharedlibrary.dto.application.RecommenderRequest request) {
        if (request == null) return null;

        return new EmbeddedRecommender(
                null, // ID will be generated
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail()
        );
    }

    /**
     * Helper method to convert a list of recommender requests to embedded recommenders.
     */
    public List<EmbeddedRecommender> toEmbeddedRecommenderList(List<rw.ac.ilpd.sharedlibrary.dto.application.RecommenderRequest> requests) {
        if (requests == null) return Collections.emptyList();

        return requests.stream()
                .map(this::toEmbeddedRecommender)
                .collect(Collectors.toList());
    }
}