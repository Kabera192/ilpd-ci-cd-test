/**
 * This file defines the InstallmentFeeUserRequest DTO used for linking fee users to installments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.installmentfeeuser;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentFeeUserRequest {

    @NotBlank(message = "Fee user ID cannot be null or blank")
    @RestrictedString
    private String feeUserId;

    @NotBlank(message = "Installment ID cannot be null or blank")
    @RestrictedString
    private String installmentId;
}
