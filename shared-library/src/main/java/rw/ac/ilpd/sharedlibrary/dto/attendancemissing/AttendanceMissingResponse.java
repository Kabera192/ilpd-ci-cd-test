/**
 * This file defines the AttendanceMissingResponse DTO used for returning attendance missing data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.attendancemissing;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceMissingResponse {
    private String id;
    private String activityOccurrenceId;
    private String userId;
    private String createdAt;
}
