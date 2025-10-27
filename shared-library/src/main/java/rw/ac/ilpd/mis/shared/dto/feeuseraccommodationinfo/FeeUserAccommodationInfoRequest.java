/**
 * This file defines the FeeUserAccommodationInfoRequest DTO used for handling fee user accommodation information.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.feeuseraccommodationinfo;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserAccommodationInfoRequest {
    @NotBlank(message = "Fee user ID cannot be null or blank")
    private String feeUserId;

    @NotBlank(message = "Start date cannot be null or blank")
    private String startDate;

    @NotBlank(message = "End date cannot be null or blank")
    private String endDate;

    @NotBlank(message = "Reservation ID cannot be null or blank")
    private String reservationId;
}
