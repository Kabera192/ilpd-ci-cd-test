/*
 * File: IntakeAssessmentGroupStudentDto.java
 * 
 * Description: Data Transfer Object representing a student's membership in an intake assessment group.
 *              Contains information about the group, student, leadership status, and relevant timestamps.
 *              Fields:
 *                - id: Unique identifier for this group-student association.
 *                - groupId: Unique identifier of the assessment group. (Required)
 *                - studentId: Unique identifier of the student. (Required)
 *                - isLeader: Indicates if the student is a group leader. (Required)
 *                - createdAt: Timestamp when the association was created. (Required)
 *                - joinedAt: Timestamp when the student joined the group. (Required)
 *                - leftAt: Timestamp when the student left the group. (Required)
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.assessment;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntakeAssessmentGroupStudentRequest {

    @NotBlank(message = "Group ID is required")
    @RestrictedString
    private String groupId;

    @NotBlank(message = "Student ID is required")
    @RestrictedString
    private String studentId;

    @NotNull(message = "Is leader flag is required")
    private Boolean isLeader;

    @NotBlank(message = "Joined at is required")
    @RestrictedString
    private String joinedAt;

    @NotBlank(message = "Left at is required")
    @RestrictedString
    private String leftAt;
}