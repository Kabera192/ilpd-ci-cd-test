/**
 * Request payload for creating or updating a role in the ILPD system.
 *
 * <p>This DTO contains role ID, role name, and associated unit ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest
{
    @NotBlank(message = "Role name should not be null")
    private String name;

    // This is the String of Unit
    @NotBlank(message = "Unit id should not be null")
    private String unitId;
}
