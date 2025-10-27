/*
 * ApplicationDto.java
 * 
 * This class is used to represent an application.
 * 
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
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
public class ApplicationRequest {

    @NotNull(message = "User ID is required")
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotNull(message = "Intake ID is required")
    @NotBlank(message = "Intake ID cannot be blank")
    private String intakeId;

    @NotNull(message = "Next of Kin ID is required")
    @NotBlank(message = "Next of Kin ID cannot be blank")
    private String nextOfKinId;
}
