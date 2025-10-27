package rw.ac.ilpd.sharedlibrary.dto.lecturer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Slf4j
public class LecturerRequest {

    @NotBlank(message = "User is required")
    @ValidUuid(message = "Invalid user input format")
    private String userId;

    @NotBlank(message = "Employee status is required")
    @Pattern(
            regexp = "^(PERMANENT|TEMPORARY|CONTRACT|PART_TIME)$",
            message = "Employment Status must be either ' PERMANENT' or ' TEMPORARY'")
    private String engagementType;

    private  String endDate;

    @AssertTrue(message = "Invalid engagement/end date combination")
    @JsonIgnore
    public boolean isValidEngagement() {
        if (engagementType == null) return true;

        if ("CONTRACT".equals(engagementType)
                || "TEMPORARY".equals(engagementType)
                || "PART_TIME".equals(engagementType)) {

            if (endDate == null || endDate.isBlank()) {
                return false;
            }
            return isValidDate();
        }

        return true;
    }
    @AssertTrue(message = "Check your date format or The date you provide is after the date of today\n hint he correct date format is YYYY-mmm-dd ex(2025-06-10)")
    @JsonIgnore
    public boolean isValidDate() {
        try {
            LocalDate endDate = LocalDate.parse(getEndDate());
            return endDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            log.error("Exception {}",e);
            return false;
        }
    }

    public static void main(String[] args) {

    }
}