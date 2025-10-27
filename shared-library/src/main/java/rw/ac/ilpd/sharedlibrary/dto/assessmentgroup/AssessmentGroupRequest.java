/**
 * This file defines the AssessmentGroupRequest DTO used for submitting
 * assessment group data into the system.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmentgroup;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentGroupRequest {

    @NotBlank(message = "Name cannot be null")
    @RestrictedString
    private String name;
}
