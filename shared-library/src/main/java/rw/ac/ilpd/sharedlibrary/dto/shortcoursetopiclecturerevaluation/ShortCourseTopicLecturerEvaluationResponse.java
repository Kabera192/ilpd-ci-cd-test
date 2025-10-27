/**
 * Response payload representing an evaluation of a short course topic lecturer in the ILPD system.
 *
 * <p>This DTO returns the evaluation ID, linked lecturer assignment, prior evaluation form (if any), and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicLecturerEvaluationResponse {
    private String id;
    private String shortCourseTopicLecturerId;
    private String priorEvaluationFormId;
    private String createdAt;
}
