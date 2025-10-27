/**
 * This file defines the ActivityTypeRequest DTO used for submitting
 * new activity type data into the system.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.activitytype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @Pattern(regexp = "LECTURE|LAB|SEMINAR", message = "Type must be either LECTURE, LAB, or SEMINAR")
    private String name;

    private String description;  // Optional
}
