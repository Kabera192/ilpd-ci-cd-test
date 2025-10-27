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
public class DocumentVerificationStatusUpdateRequest {
    @NotNull(message = "Verification status is required")
    @NotBlank(message = "Verification status cannot be blank")
    @RestrictedString
    private String verificationStatus;

    @RestrictedString
    private String comment;
}
