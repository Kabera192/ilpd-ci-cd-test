package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.Application;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSummaryResponse;
import rw.ac.ilpd.sharedlibrary.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApplicationMapper {

    // ✅ UPDATED: Remove nextOfKinId
    public Application toEntity(ApplicationRequest request) {
        return new Application(
                null, // ID will be generated
                parseUUID(request.getUserId()),
                parseUUID(request.getIntakeId()),
                ApplicationStatus.PENDING,
                LocalDateTime.now(),
                new ArrayList<>()
        );
    }

    // ✅ UPDATED: Remove nextOfKinId, add academicBackgrounds list
    public ApplicationResponse toResponse(Application entity) {
        return ApplicationResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId().toString())
                .intakeId(entity.getIntakeId().toString())
                // REMOVED: nextOfKinId
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .documentSubmissions(Collections.emptyList())
                .sponsors(Collections.emptyList())
                .academicBackgrounds(Collections.emptyList()) // Initialize as empty list
                .totalDocuments(0)
                .totalSponsors(0)
                .hasAcademicBackground(false)
                .totalAcademicBackgrounds(0)
                .build();
    }

    // ✅ UPDATED: Handle multiple academic backgrounds instead of single one
    public ApplicationResponse toRichResponse(Application entity,
                                              List<ApplicationDocumentSubmissionResponse> documents,
                                              List<ApplicationSponsorResponse> sponsors,
                                              List<AcademicBackgroundResponse> academicBackgrounds) {

        List<ApplicationDocumentSubmissionResponse> safeDocuments =
                documents != null ? documents : Collections.emptyList();
        List<ApplicationSponsorResponse> safeSponsors =
                sponsors != null ? sponsors : Collections.emptyList();
        List<AcademicBackgroundResponse> safeAcademicBackgrounds =
                academicBackgrounds != null ? academicBackgrounds : Collections.emptyList();

        int documentCount = safeDocuments.size();
        int sponsorCount = safeSponsors.size();
        boolean hasAcademic = !safeAcademicBackgrounds.isEmpty();
        int academicCount = safeAcademicBackgrounds.size();

        return ApplicationResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId().toString())
                .intakeId(entity.getIntakeId().toString())
                // REMOVED: nextOfKinId
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .documentSubmissions(safeDocuments)
                .sponsors(safeSponsors)
                .academicBackgrounds(safeAcademicBackgrounds) // Multiple academic backgrounds
                .totalDocuments(documentCount)
                .totalSponsors(sponsorCount)
                .hasAcademicBackground(hasAcademic)
                .totalAcademicBackgrounds(academicCount)
                .build();
    }

    // ✅ UPDATED: Remove nextOfKinId from summary response
    public ApplicationSummaryResponse toSummaryResponse(Application application, int documentCount,
                                                        int sponsorCount, boolean hasAcademicBackground,
                                                        int academicBackgroundCount) {
        return ApplicationSummaryResponse.builder()
                .id(application.getId())
                .userId(application.getUserId().toString())
                .intakeId(application.getIntakeId().toString())
                // REMOVED: nextOfKinId
                .status(application.getStatus().name())
                .createdAt(application.getCreatedAt())
                .totalDocuments(documentCount)
                .totalSponsors(sponsorCount)
                .hasAcademicBackground(hasAcademicBackground)
                .totalAcademicBackgrounds(academicBackgroundCount)
                .build();
    }

    // ✅ UPDATED: Remove nextOfKinId from update method
    public Application updateEntity(Application existing, ApplicationRequest request) {
        existing.setUserId(parseUUID(request.getUserId()));
        existing.setIntakeId(parseUUID(request.getIntakeId()));
        // REMOVED: nextOfKinId update
        return existing;
    }

    // ✅ KEEP: All other existing methods
    public List<ApplicationResponse> toResponseList(List<Application> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private UUID parseUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + uuidString, e);
        }
    }
}