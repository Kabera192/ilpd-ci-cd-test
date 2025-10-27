/**
 * IntakeResponse DTO.
 * Represents the response data for an intake, including related program, study mode, location, dates, capacity, status, and associated entities.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intake;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocResponse;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumResponse;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntakeResponse {
    private String id;
    private String name;
    private ProgramResponse program;
    private StudyModeSessionResponse studyModeSession;
    private String locationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime applicationOpeningDate;
    private LocalDateTime applicationDueDate;
    private LocalDateTime graduationDate;
    private Integer maxNumberOfStudents;
    private IntakeStatus status; // "OPEN" or "CLOSED"
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private CurriculumResponse curriculum;
    private String deliberationGroupId;
    private InstitutionShortCourseSponsorResponse institution;
    private String deliberationDistinctionGrpId;
    private List<ApplicationRequiredDocIntakeResponse> applicationRequiredDocs;
}
