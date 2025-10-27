/**
 * Request payload for defining a prohibited transfer couple in the ILPD system.
 *
 * <p>This DTO contains two session IDs.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.prohibitedtransfercouple;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
    public class ProhibitedTransferCoupleRequest {

        @ValidUuid(message = "Invalid Study session mode format")
        @NotBlank(message = "First session ID cannot be null")
        private String session1Id;

        @ValidUuid(message = "Invalid Study session mode format")
        @NotBlank(message = "Second session ID cannot be null")
        private String session2Id;


    }
