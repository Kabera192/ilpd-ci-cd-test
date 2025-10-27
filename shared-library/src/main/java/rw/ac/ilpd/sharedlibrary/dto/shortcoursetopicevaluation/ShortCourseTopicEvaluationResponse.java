/**
 * Response payload representing a short course topic evaluation in the ILPD system.
 *
 * <p>This DTO returns evaluation ID, topic, student, quiz references, and the grade.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicEvaluationResponse {
    private String id;
    private String topicId;
    private String studentId;
    private String quizId;
    private BigDecimal grade;
}
