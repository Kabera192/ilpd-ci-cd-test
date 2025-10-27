/**
 * IntakeRequest DTO.
 * Represents the request data for creating or updating an intake, including details such as program, study mode, location, dates, capacity, and status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intake;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import rw.ac.ilpd.mis.shared.enums.IntakeStatus;

import java.time.LocalDateTime;


@Getter
@Setter
public class IntakeRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    private String name;

    @NotBlank(message = "Program ID cannot be null or blank")
    private String programId;

    @NotBlank(message = "Study mode session ID cannot be null or blank")
    private String studyModeSessionId;

    @NotBlank(message = "Location ID cannot be null or blank")
    private String locationId;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;

    @NotNull(message = "Application opening date cannot be null")
    private LocalDateTime applicationOpeningDate;

    @NotNull(message = "Application due date cannot be null")
    private LocalDateTime applicationDueDate;

    // can be null
    private LocalDateTime graduationDate;

    @PositiveOrZero(message = "Max number of students must be positive or zero")
    private Integer maxNumberOfStudents;

    @PositiveOrZero(message = "Max student offset must be positive or 0")
    private Integer maxStudentsOffset;

    private String curriculumId;

    @NotBlank(message = "Deliberation Group id should not be null or blank")
    private String deliberationGroupId;

    private String institutionId; // Can be null or blank

    private String deliberationDistinctionGrpId;
}
