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
package rw.ac.ilpd.sharedlibrary.dto.assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAssessmentSubmissionRequest {

    @NotBlank(message = "Module component assessment ID is required")
    private String moduleComponentAssessmentId;

    // this holds id's for either a group or student depending on the
    // assessment mode.
    @NotBlank(message = "Subject ID is required")
    private String subjectId;

    private Boolean isCurrent = true;

    private String comment;

    private List<MultipartFile> documents;
}