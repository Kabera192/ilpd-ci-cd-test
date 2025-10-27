/**
 * This file defines the FeeUserAccommodationInfoResponse DTO used for returning fee user accommodation information.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.feeuseraccommodationinfo;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserAccommodationInfoResponse {
    private String id;
    private String feeUserId;
    private String startDate;
    private String endDate;
    private String reservationId;
    private String createdAt;
}
