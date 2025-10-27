/**
 * This file defines the DeliberationRuleGroupRequest DTO used for handling deliberation rule group data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRuleGroupRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    private String name;
    private ValidityStatus status;

}
