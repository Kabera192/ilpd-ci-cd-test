/*
 * File: AcademicBackgroundDto.java
 *
 * Description: Data Transfer Object representing the academic background of an applicant.
 *              Contains information about the university attended, degree obtained, and the duration of study.
 *              Includes validation constraints for required fields and date logic.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicBackgroundRequest {

    @NotNull(message = "Application ID is required")
    @NotBlank(message = "Application ID cannot be blank")
    private String applicationId;

    @NotNull(message = "University ID is required")
    @NotBlank(message = "University ID cannot be blank")
    private String universityId;

    @NotNull(message = "Degree is required")
    @NotBlank(message = "Degree cannot be blank")
    @Size(min = 2, max = 200, message = "Degree must be between 2 and 200 characters")
    private String degree;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // List of recommender IDs to be converted to objects
    @NotEmpty(message = "At least one recommender is required")
    private List<String> recommenderIds;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        return endDate == null || startDate == null || endDate.isAfter(startDate);
    }
}
