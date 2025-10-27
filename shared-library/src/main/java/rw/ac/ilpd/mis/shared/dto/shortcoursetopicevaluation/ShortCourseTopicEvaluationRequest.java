/**
 * Request payload for submitting a short course topic evaluation in the ILPD system.
 *
 * <p>This DTO includes topic ID, student ID, quiz ID, and the grade awarded.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.shortcoursetopicevaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicEvaluationRequest {

    @NotBlank(message = "Topic ID cannot be null or blank")
    private String topicId;

    @NotBlank(message = "Student ID cannot be null or blank")
    private String studentId;

    @NotBlank(message = "Quiz ID cannot be null or blank")
    private String quizId;

    @NotNull(message = "Grade cannot be null")
    private BigDecimal grade;
}
