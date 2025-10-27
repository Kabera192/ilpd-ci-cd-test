/**
 * Response payload representing a short course topic in the ILPD system.
 *
 * <p>This DTO returns details such as ID, intake, name, description, creator, creation date, and deletion status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.shortcoursetopic;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicResponse {
    private String id;
    private String intakeId;
    private String name;
    private String description;
    private String createdBy;
    private LocalDate createdAt;
    private Boolean deletedStatus;
}
