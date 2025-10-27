/**
 * Request payload for registering a payment in the ILPD system.
 *
 * <p>This DTO contains details such as the user to be charged and the payment amount.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.payment;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @RestrictedString
    private String feeUserId;
    @RestrictedString
    private String transactionNumber;
    @RestrictedString
    private String creditAccount;
    @RestrictedString
    private String paymentMethod;
    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
}
