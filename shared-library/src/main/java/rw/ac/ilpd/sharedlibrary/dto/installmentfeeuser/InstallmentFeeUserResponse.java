/**
 * This file defines the InstallmentFeeUserResponse DTO used for returning the link details between fee users and installments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.installmentfeeuser;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentFeeUserResponse {
    private String id;
    private String feeUserId;
    private String installmentId;
}
