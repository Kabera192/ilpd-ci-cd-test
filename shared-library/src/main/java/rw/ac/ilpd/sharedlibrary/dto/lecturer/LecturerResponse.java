/**
 * lecturerResponse DTO.
 * Represents the response data for an lecturer including ID, user ID, and status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.lecturer;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerResponse {
    private String id;
    private String userId;
    private String engagementType;
    private String activeStatus;
    private String endDate;
    private String createdAt;
}
