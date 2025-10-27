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
package rw.ac.ilpd.mis.shared.dto.assessment;



import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntakeAssessmentGroupStudentResponse {
    private String id;
    private String groupId;
    private String studentId;
    private Boolean isLeader;
    private String createdAt;
    private String joinedAt;
    private String leftAt;
}