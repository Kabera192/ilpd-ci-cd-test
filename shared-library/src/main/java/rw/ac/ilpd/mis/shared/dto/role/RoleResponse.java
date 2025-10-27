/**
 * Response payload representing a role entity in the ILPD system.
 *
 * <p>This DTO contains role ID, name, and associated unit ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.role;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private String id;
    private String name;
    private String unitId;
}
