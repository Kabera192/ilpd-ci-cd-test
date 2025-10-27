/**
 * Response payload representing a setting entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, name, acronym, key, value, and deletion status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.setting;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponse {
    private String id;
    private String name;
    private String acronym;
    private String key;
    private String value;
    private Boolean deletedStatus;
}
