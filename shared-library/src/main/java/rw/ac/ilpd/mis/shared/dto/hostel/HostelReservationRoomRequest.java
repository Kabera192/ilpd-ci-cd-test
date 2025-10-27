/*
* HostelReservationRoomRequest.java
*
* Authors: Kabera Clapton(ckabera6@gmail.com)
*
* This dto is an abstraction of the HostelReservationRoom entity.
* */
package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelReservationRoomRequest
{
    // am not sure if we need this field since we already have the reservation UUID
    private String reservationCode;

    @NotBlank(message = "The reservation String cannot be null or blank")
    private String reservation;

    @NotBlank(message = "The reservedRoom String cannot be null or blank")
    private String reservedRoom;

    // not sure if we need this field too since it is also captured in the
    // reservation table which
    // has a relationship to the ExternalUser entity which is the client.
    @NotBlank(message = "The client String cannot be null or blank")
    private String client;

    @Positive(message = "The actualPrice cannot have a negative value")
    private Double actualPrice;

//    @FutureOrPresent(message = "Check in time cannot be in the past")
    private String checkInTime;

//    @FutureOrPresent(message = "Check out time cannot be in the past")
    private String checkOutTime;
}
