/**
 * This file defines the FeeUserResponse DTO used for returning fee user payment data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.feeuser;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserResponse {
    private String id;
    private String feeId;
    private String userId;
    private BigDecimal actualAmount;
    private String status;
    private String requestId;
    private String applicationId;
    private String createdAt;
}
