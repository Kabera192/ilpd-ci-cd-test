/**
 * Request payload for creating or updating a role in the ILPD system.
 *
 * <p>This DTO contains role ID, role name, and associated unit ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    private UUID id;

    private String name;

    private UUID unitId;

    private Set<UUID> privileges;

    private Set<String> privilegeNames;

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
