/**
 * This file defines the DeliberationRuleGroupRequest DTO used for handling deliberation rule group data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.deliberationrulegroup;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.ValidityStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRuleGroupRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;
    private ValidityStatus status;

}
