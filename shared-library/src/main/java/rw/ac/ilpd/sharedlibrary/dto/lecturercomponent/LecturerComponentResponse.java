/**
 * lecturerComponentResponse DTO.
 * Represents the response data for an lecturer-component association including active status and creation timestamp.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.lecturercomponent;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerComponentResponse {
    private String id;
    private String lecturerId;
    private String componentId;
    private Boolean activeStatus;
    private String createdAt;
}
