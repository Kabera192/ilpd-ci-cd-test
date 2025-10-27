/**
 * This file defines the InstallmentRequest DTO used for submitting installment details related to fees.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.installment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentRequest {

    @NotBlank(message = "Fee ID cannot be null or blank")
    private String feeId;

    @NotNull(message = "Installment number cannot be null")
    @Positive(message = "Installment number must be positive")
    private Integer installmentNumber;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;
}
