/**
 * IntakeAssessmentGroupResponse DTO.
 * Represents the response payload for an intake assessment group, including IDs, name, creation timestamp, and associated students.
 * <p>
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentResponse;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeAssessmentGroupResponse
{
    private String id;
    private String componentId;
    private String intakeId;
    private String name;
    private String createdAt;
    private List<IntakeAssessmentGroupStudentResponse> students;
}