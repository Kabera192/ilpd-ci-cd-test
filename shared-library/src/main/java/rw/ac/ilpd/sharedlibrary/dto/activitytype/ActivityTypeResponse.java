/**
 * This file defines the ActivityTypeResponse DTO used to represent
 * the response structure for an activity type entity.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activitytype;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeResponse {
    private String id;
    private String name;
    private String description;
}
