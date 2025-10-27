/**
 * This file defines the FeeUserRequest DTO used for handling fee user payment data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.feeuser;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserRequest {
    @NotBlank(message = "Fee ID cannot be null")
    @RestrictedString
    private String feeId;

    @NotBlank(message = "User ID cannot be null")
    @RestrictedString
    private String userId;

    @NotNull(message = "Actual amount cannot be null")
    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    private BigDecimal actualAmount;

    @NotBlank(message = "Status cannot be null")
    @RestrictedString
    private String status;
    @RestrictedString
    private String requestId; // Nullable
    @RestrictedString
    private String applicationId; // Nullable
}
