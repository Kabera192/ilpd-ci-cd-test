/**
 * Response payload representing the association between course material and a short course topic lecturer in the ILPD system.
 *
 * <p>This DTO returns the ID of the association, course material ID, and topic lecturer ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortCourseTopicMaterialResponse {
    private String id;
    private String courseMaterialId;
    private String topicLecturerId;
}
