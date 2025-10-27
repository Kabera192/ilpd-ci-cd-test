/**
 * IntakeResponse DTO.
 * Represents the response data for an intake, including related program, study mode, location, dates, capacity, status, and associated entities.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intake;

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.curriculum.CurriculumResponse;
import rw.ac.ilpd.mis.shared.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorResponse;
import rw.ac.ilpd.mis.shared.dto.program.ProgramResponse;
import rw.ac.ilpd.mis.shared.dto.studymodesession.StudyModeSessionResponse;
import rw.ac.ilpd.mis.shared.enums.IntakeStatus;

import java.time.LocalDateTime;
import java.util.List;


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
    private Integer maxStudentsOffset;
    private IntakeStatus status; // "OPEN" or "CLOSED"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CurriculumResponse curriculum;
    private String deliberationGroupId;
    private InstitutionShortCourseSponsorResponse institution;
    private String deliberationDistinctionGrpId;
    private List<ApplicationRequiredDocIntakeResponse> applicationRequiredDocs;
}
