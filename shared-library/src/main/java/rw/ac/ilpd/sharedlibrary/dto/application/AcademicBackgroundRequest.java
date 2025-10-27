package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicBackgroundRequest {

    // Changed from applicationId to userId to support user-centric approach
    @NotNull(message = "User ID is required")
    @NotBlank(message = "User ID cannot be blank")
    @RestrictedString
    private String userId;

    @NotNull(message = "University ID is required")
    @NotBlank(message = "University ID cannot be blank")
    @RestrictedString
    private String universityId;

    @NotNull(message = "Degree is required")
    @NotBlank(message = "Degree cannot be blank")
    @Size(min = 2, max = 200, message = "Degree must be between 2 and 200 characters")
    @RestrictedString
    private String degree;

    @NotNull(message = "Start date is required")
    @PastOrPresent(message = "Start date cannot be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // Changed from recommenderIds (UUIDs) to full recommender objects with all information
    @NotEmpty(message = "At least one recommender is required")
    @Valid
    private List<RecommenderRequest> recommenders;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        return endDate == null || startDate == null || endDate.isAfter(startDate);
    }
}
