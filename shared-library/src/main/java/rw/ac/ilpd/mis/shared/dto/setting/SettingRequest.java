/**
 * Request payload for creating or updating a setting in the ILPD system.
 *
 * <p>This DTO includes name, acronym, key, value.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.setting;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingRequest
{
    private String name;

    private String acronym;

    @NotBlank(message = "Key cannot be null")
    private String key;

    @NotBlank(message = "Value cannot be null")
    private String value;

    
}
