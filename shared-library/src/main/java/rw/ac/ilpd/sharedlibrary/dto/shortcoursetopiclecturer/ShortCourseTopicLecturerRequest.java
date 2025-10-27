/**
 * Request payload for assigning a lecturer to a short course topic in the ILPD system.
 *
 * <p>This DTO includes references to the topic, lecturer, and room.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortCourseTopicLecturerRequest {

    @NotBlank(message = "Topic ID cannot be null")
    @RestrictedString
    private String topicId;

    @NotBlank(message = "Lecturer ID cannot be null")
    @RestrictedString
    private String lecturerId;

    @NotBlank(message = "Room ID cannot be null")
    @RestrictedString
    private String roomId;
}
