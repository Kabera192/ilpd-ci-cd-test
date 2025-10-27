/*
 * File: ShortCourseStudentRequest.java
 * 
 * Description: Data Transfer Object representing a student enrolled in a short course.
 *              Contains information about the student's enrollment, including intake ID,
 *              user ID, status, and creation timestamp. Validation constraints ensure
 *              that intakeId, userId, status, and createdAt are not null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcourse;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortCourseStudentRequest {

    @NotBlank(message = "Intake ID is required")
    @RestrictedString
    private String intakeId;

    @NotBlank(message = "User ID is required")
    @RestrictedString
    private String userId;

    @NotBlank(message = "Status is required")
    @RestrictedString
    private String status;
}