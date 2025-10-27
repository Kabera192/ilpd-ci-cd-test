/**
 * Response payload representing a prohibited transfer couple in the ILPD system.
 *
 * <p>This DTO returns the ID of the couple, the linked sessions, and deletion status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.prohibitedtransfercouple;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProhibitedTransferCoupleResponse {
    private String id;
    private String session1Id;
    private String session2Id;
    private Boolean deletedStatus;
}
