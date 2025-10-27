package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private String id;
    private String userId;
    private String intakeId;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<ApplicationDocumentSubmissionResponse> documentSubmissions;
    private List<ApplicationSponsorResponse> sponsors;

    private List<AcademicBackgroundResponse> academicBackgrounds;

    private int totalDocuments;
    private int totalSponsors;
    private boolean hasAcademicBackground;

    private int totalAcademicBackgrounds;

}