/**
 * This file defines the FeeRequest DTO used for handling fee data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.fee;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.FeeScopeType;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeRequest {
    @NotBlank(message = "Name cannot be blank")
    @RestrictedString
    private String name;

    @NotBlank(message = "Scope cannot be null")
    @RestrictedString
    private String scope;

    @NotBlank(message = "Session ID cannot be null")
    @RestrictedString
    private String sessionId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;
}
