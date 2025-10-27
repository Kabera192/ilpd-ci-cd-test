/**
 * IntakeRequest DTO.
 * Represents the request data for creating or updating an intake, including details such as program, study mode, location, dates, capacity, and status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intake;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.optional.OptionalValidUuid;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class IntakeRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    @RestrictedString
    private String name;

    @NotBlank(message = "Program ID cannot be null or blank")
    @ValidUuid(message = "Specified program format is not valid")
    private String programId;

    @OptionalValidUuid(message = "Specified study mode session format is not valid")
    private String studyModeSessionId;// Can be null or blank on CLE

    @NotBlank(message = "Campus is required")
    @RestrictedString
    private String locationId;

    @NotNull(message = "Start date is  required")
//    @FutureOrPresent(message = "Intake Starting date must be today or in the future")
    private LocalDateTime startDate;

    @NotNull(message = "Intake end date is  required")
//    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

//    @FutureOrPresent(message = "Application opening date must be in the future or present")
    @NotNull(message = "Application opening date is required")
    private LocalDateTime applicationOpeningDate;

//    @Future(message = "Application Deadline date must be in the future")
    @NotNull(message = "Application due date is required")
    private LocalDateTime applicationDueDate;

    // can be null
    private LocalDateTime graduationDate;

    @PositiveOrZero(message = "Max number of students must be positive or zero")
    private Integer maxNumberOfStudents;

    @OptionalValidUuid(message = "Specified curriculum format is not valid")
    private String curriculumId; // Can be null or blank on CLE

    private String deliberationGroupId;// Can be null or blank on CLE

    @OptionalValidUuid(message = "Specified institution format is not valid")
    private String institutionId; // Can be null or blank

    private String deliberationDistinctionGrpId;// Can be null or blank on CLE
//    endDate > startDate
    @AssertTrue(message = "End date must be after start date")
    @JsonIgnore
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }
    //    applicationDueDate > applicationOpeningDate
    @JsonIgnore
    @AssertTrue(message = "Application Deadline must be after opening Date date")
    public boolean isApplicationDateValidated() {
        if (applicationOpeningDate == null || applicationDueDate == null) {
            return true;
        }
        return applicationDueDate.isAfter(applicationOpeningDate);
    }
    //    startDate > applicationOpeningDate
    @JsonIgnore
    @AssertTrue(message = "Intake opening date must be after Application starting date")
    public boolean isEndDateAfterApplicationOpeningDate() {
        if (startDate == null || applicationOpeningDate == null) {
            return true;
        }
        return startDate.isAfter(applicationOpeningDate);
    }
    //    endDate > applicationDueDate
    @JsonIgnore
    @AssertTrue(message = "Intake end date must be after application due Date")
    public boolean isEndDateAfterApplicationDueDate() {
        if (endDate == null || applicationDueDate == null) {
            return true;
        }
        return endDate.isAfter(applicationDueDate);
    }
    @JsonIgnore
    @AssertTrue(message = "Graduation  closing date must be after Intake end Date")
    public boolean isValidGraduationDate() {
        if (endDate == null || graduationDate == null) {
            return true;
        }
        return graduationDate.isAfter(endDate);
    }
}
