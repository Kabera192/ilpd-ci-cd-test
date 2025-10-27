/*
 * ApplicationDto.java
 *
 * This class is used to represent an application.
 *
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

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
    private String nextOfKinId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // ✅ ADD THESE MISSING FIELDS
    private List<ApplicationDocumentSubmissionResponse> documentSubmissions;
    private List<ApplicationSponsorResponse> sponsors;
    private AcademicBackgroundResponse academicBackground;

    // Computed fields using primitive types to avoid null values
    private int totalDocuments;
    private int totalSponsors;
    private boolean hasAcademicBackground;
}
