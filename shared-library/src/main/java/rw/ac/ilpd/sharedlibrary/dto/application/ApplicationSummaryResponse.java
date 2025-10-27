package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSummaryResponse {

    private String id;
    private String userId;
    private String intakeId;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private int totalDocuments;
    private int totalSponsors;
    private boolean hasAcademicBackground;

    private int totalAcademicBackgrounds;
}