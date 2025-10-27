package rw.ac.ilpd.mis.shared.dto.userprofile;


import java.time.LocalDate;
import java.util.List;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkExperienceResponse {

    private String id;

    private String userId;

    private String companyName;

    private String positionTitle;

    private String workMode;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyWorking;

    private String description;

    private String documentId;

    private JobLocationResponse jobLocation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class JobLocationResponse {
        private String country;

        private String city;

        private String addressLine;
    }
}