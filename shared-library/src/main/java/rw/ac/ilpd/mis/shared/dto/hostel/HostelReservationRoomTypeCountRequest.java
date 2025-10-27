/*
 * HostelReservationRoomTypeCountRequest.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This dto is an abstraction of the HostelReservationRoomTypeCount entity.
 * */
package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelReservationRoomTypeCountRequest
{
    @NotBlank(message = "The hostelReservation String cannot be null")
    private String hostelReservationId;

    @NotBlank(message = "The reservationRoomType String cannot be null")
    private String roomTypeId;

    @NotNull(message = "The numberOfRooms cannot be null")
    @Positive(message = "The numberOfRooms cannot have a negative value")
    private int numberOfRooms;
}
