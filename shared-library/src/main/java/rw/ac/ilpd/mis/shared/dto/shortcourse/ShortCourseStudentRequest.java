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
package rw.ac.ilpd.mis.shared.dto.shortcourse;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortCourseStudentRequest {

    @NotBlank(message = "Intake ID is required")
    private String intakeId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Status is required")
    private String status;
}