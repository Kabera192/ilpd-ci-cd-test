/**
 * This file defines the FeeUserRequest DTO used for handling fee user payment data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.feeuser;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserRequest {
    @NotBlank(message = "Fee ID cannot be null")
    private String feeId;

    @NotBlank(message = "User ID cannot be null")
    private String userId;

    @NotNull(message = "Actual amount cannot be null")
    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    private BigDecimal actualAmount;

    @NotBlank(message = "Status cannot be null")
    private String status;

    private String requestId; // Nullable
    private String applicationId; // Nullable
}
