/**
 * This file defines the InstallmentFeeUserRequest DTO used for linking fee users to installments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.installmentfeeuser;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentFeeUserRequest {

    @NotBlank(message = "Fee user ID cannot be null or blank")
    private String feeUserId;

    @NotBlank(message = "Installment ID cannot be null or blank")
    private String installmentId;
}
