/**
 * This file defines the FeeRequest DTO used for handling fee data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.fee;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Scope cannot be null")
    private String scope;

    @NotBlank(message = "Session ID cannot be null")
    private String sessionId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;
}
