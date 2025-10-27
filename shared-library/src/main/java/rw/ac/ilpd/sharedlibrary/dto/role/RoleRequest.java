/**
 * Request payload for creating or updating a role in the ILPD system.
 *
 * <p>This DTO contains role ID, role name, and associated unit ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest
{
    @NotBlank(message = "Role name should not be null")
    @RestrictedString
    private String name;

    // This is the String of Unit
    @NotBlank(message = "Unit id should not be null")
    @RestrictedString
    private String unitId;
}
