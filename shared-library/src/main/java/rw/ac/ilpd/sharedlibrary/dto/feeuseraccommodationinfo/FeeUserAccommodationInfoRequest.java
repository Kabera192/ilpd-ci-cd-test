/**
 * This file defines the FeeUserAccommodationInfoRequest DTO used for handling fee user accommodation information.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.feeuseraccommodationinfo;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeUserAccommodationInfoRequest {
    @NotBlank(message = "Fee user ID cannot be null or blank")
    @RestrictedString
    private String feeUserId;

    @NotBlank(message = "Start date cannot be null or blank")
    @RestrictedString
    private String startDate;

    @NotBlank(message = "End date cannot be null or blank")
    @RestrictedString
    private String endDate;

    @NotBlank(message = "Reservation ID cannot be null or blank")
    @RestrictedString
    private String reservationId;
}
