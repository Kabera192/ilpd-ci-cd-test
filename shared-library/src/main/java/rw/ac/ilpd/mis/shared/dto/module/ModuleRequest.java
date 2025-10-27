/**
 * Request payload for creating or updating a module in the ILPD system.
 *
 * <p>This DTO includes details such as name, code, order, type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.ModuleType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Code cannot be blank")
    private String code;
    @NotNull(message = "Module type cannot be blank")
    private ModuleType type; // "MOOT_COURT", "INTERNSHIP", "MODULE".
}
