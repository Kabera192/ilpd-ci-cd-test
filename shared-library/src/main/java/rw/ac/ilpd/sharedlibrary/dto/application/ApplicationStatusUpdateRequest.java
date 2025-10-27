package rw.ac.ilpd.sharedlibrary.dto.application;

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
public class ApplicationStatusUpdateRequest {
    @NotNull(message = "Status is required")
    @NotBlank(message = "Status cannot be blank")
    @RestrictedString
    private String status;

    @RestrictedString
    private String comment;
}
