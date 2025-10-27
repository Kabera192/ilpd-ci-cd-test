/**
 * Request payload for registering a payment in the ILPD system.
 *
 * <p>This DTO contains details such as the user to be charged and the payment amount.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String feeUserId;
    private String transactionNumber;
    private String creditAccount;
    private String paymentMethod;
    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
}
