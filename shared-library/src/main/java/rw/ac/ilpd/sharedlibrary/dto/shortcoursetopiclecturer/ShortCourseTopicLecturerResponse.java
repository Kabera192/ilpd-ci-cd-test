/**
 * Response payload representing a short course topic lecturer assignment in the ILPD system.
 *
 * <p>This DTO returns the ID, linked topic, lecturer, room, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicLecturerResponse {
    private String id;
    private String topicId;
    private String lecturerId;
    private String roomId;
    private String createdAt;
}
