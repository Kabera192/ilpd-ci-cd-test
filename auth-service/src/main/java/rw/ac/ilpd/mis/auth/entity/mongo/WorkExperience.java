package rw.ac.ilpd.mis.auth.entity.mongo;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.WorkMode;
import java.time.LocalDate;

@Document(collection = "work_experiences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkExperience {

    @Id
    private String id;

    @CreatedBy
    private String userId;

    private String companyName;

    private String positionTitle;

    private WorkMode workMode;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyWorking;

    private String description;

    private String documentId;

    private JobLocation jobLocation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class JobLocation {
        private String country;

        private String city;

        private String addressLine;
    }
}