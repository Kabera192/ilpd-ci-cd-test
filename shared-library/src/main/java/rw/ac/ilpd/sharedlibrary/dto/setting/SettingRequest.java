/**
 * Request payload for creating or updating a setting in the ILPD system.
 *
 * <p>This DTO includes name, acronym, key, value.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.setting;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SettingRequest {
    @RestrictedString
    private String name;
    @RestrictedString
    private String description;
    @RestrictedString
    private String category;
    @RestrictedString
    private String acronym;

    @NotBlank(message = "Key cannot be null")
    @RestrictedString
    private String key;

    @NotBlank(message = "Value cannot be null")
    @RestrictedString
    private String value;

}
