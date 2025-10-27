/**
 * Response payload representing a payment in the ILPD system.
 *
 * <p>This DTO includes details such as payment ID, fee user reference, amount, and timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.payment;

import lombok.*;
import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String feeUserId;
    private BigDecimal amount;
    private String createdAt;
}
