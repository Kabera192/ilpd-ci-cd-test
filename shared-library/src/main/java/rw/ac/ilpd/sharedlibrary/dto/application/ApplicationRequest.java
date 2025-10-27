package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Intake ID is required")
    private String intakeId;

}