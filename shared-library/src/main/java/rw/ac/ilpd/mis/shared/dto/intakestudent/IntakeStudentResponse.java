/**
 * IntakeStudentResponse DTO.
 * Represents the response payload for a student's association with an intake, including enforcement details, status, and timestamps.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intakestudent;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeStudentResponse {
    private String id;
    private String studentId;
    private String intakeId;
    private Boolean isEnforced;
    private String enforcementComment;
    private String status;
    private String createdAt;
    private Boolean isRetaking;
}
