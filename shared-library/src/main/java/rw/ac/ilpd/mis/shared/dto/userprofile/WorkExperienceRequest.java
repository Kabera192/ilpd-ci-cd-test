package rw.ac.ilpd.mis.shared.dto.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.address.OptionalValidAddress;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public class WorkExperienceRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 3, message = "Company name has have least 3 character")
    private String companyName;

    @NotBlank(message = "Position title is required")
    private String positionTitle;

    @NotBlank(message = "Work mode is required")
    @Pattern(regexp = "^(ON_SITE|REMOTE|HYBRID)$", message = "Working mode is either ON_SITE, REMOTE, HYBRID")
    private String workMode;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Starting date can not be in future")
    private LocalDate startDate;

    @PastOrPresent(message = "Ending date can not be in future")
    private LocalDate endDate;

    private boolean currentlyWorking;
    @NotBlank(message = "Description is required")
    @Size(min = 5, message = "description name has have least 5 character")
    private String description;

    private MultipartFile attachment;

    @NotBlank(message = "Country is required")
    @Size(min = 3, message = "country name has have least 3 character")
    private String country;
    private String city;
    @OptionalValidAddress(message = "Address is required")
    private String addressLine;

        @JsonIgnore
        @AssertTrue(message = "End date must be after start date if provided")
        private boolean isEndDateAfterStartDate() {
            if (startDate == null) return true;
            if (endDate == null) return true;
            return !endDate.isBefore(startDate);
        }

        @AssertTrue(message = "End date must be null if currently working")
        @JsonIgnore
        private boolean isEndDateNullWhenCurrentlyWorking() {
            if (currentlyWorking) {
                return endDate == null;
            }
            return true;
        }
}