/*
 * File: StudentAssessmentSubmissionDto.java
 * 
 * Description: Data Transfer Object representing a student's assessment submission.
 *              Contains information about the submission, including the associated module component assessment,
 *              student, submission and correction details, comments, and grade.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.assessment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAssessmentSubmissionResponse {
    private String id;
    private String moduleComponentAssessmentId;
    private UUID studentId;
    private LocalDateTime submittedAt;
    private UUID submittedBy;
    private UUID correctedBy;
    private Boolean isCurrent;
    private String comment;
    private Double grade;
    private List<String> documents;
}