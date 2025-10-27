/**
 * Request payload for submitting an evaluation of a short course topic lecturer in the ILPD system.
 *
 * <p>This DTO contains the ID of the lecturer assignment and an optional prior evaluation form ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicLecturerEvaluationRequest {

    @NotBlank(message = "Short course topic lecturer ID cannot be null or blank")
    @RestrictedString
    private String shortCourseTopicLecturerId;
    @RestrictedString
    private String priorEvaluationFormId; // Nullable
}
