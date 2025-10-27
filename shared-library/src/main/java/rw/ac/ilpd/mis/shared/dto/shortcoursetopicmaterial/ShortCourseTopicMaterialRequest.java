/**
 * Request payload for associating course material with a short course topic lecturer in the ILPD system.
 *
 * <p>This DTO includes references to the course material and the topic lecturer.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.shortcoursetopicmaterial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicMaterialRequest {

    @NotBlank(message = "Course material ID cannot be null or blank")
    private String courseMaterialId;

    @NotBlank(message = "Topic lecturer ID cannot be null or blank")
    private String topicLecturerId;
}
