package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IntakeApplicationRequiredDocEmbed
{
    @NotBlank(message = "Required Doc ID is required")
    private String requiredDocNameId;

    @NotNull(message = "Is Required is required")
    private boolean isRequired;
}
