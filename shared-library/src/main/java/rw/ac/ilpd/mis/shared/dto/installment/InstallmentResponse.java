/**
 * This file defines the InstallmentResponse DTO used for returning installment details related to fees.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.installment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentResponse {
    private String id;
    private String feeId;
    private Integer installmentNumber;
    private BigDecimal amount;
}
