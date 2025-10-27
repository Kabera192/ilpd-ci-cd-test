/**
 * Response payload representing a module entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, name, code, order, type, and delete status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.module;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResponse {
    private String id;
    private String name;
    private String code;
    private String type;
    private Boolean deleteStatus;
}
