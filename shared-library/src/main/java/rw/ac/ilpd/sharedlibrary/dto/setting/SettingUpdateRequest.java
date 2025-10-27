package rw.ac.ilpd.sharedlibrary.dto.setting;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SettingUpdateRequest {
    @RestrictedString
    private String name;
    @RestrictedString
    private String description;
    @NotBlank(message = "Value is required")
    private String value;
}
