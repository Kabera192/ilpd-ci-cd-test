package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVerificationStatusUpdateRequest {
    @NotNull(message = "Verification status is required")
    @NotBlank(message = "Verification status cannot be blank")
    private String verificationStatus;

    private String comment;
}
