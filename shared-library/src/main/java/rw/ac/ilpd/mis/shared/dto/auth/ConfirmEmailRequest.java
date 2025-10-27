/**
 * This file defines the ConfirmEmailRequest DTO used for handling email confirmation data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmEmailRequest {
    private String email;
    private String otp;
}
