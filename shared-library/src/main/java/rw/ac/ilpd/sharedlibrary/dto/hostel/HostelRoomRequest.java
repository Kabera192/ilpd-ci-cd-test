/*
* HostelRoomRequest.java
*
* Authors: Kabera Clapton(ckabera6@gmail.com)
*
* This dto is an abstraction of the HostelRoom entity.
* */
package rw.ac.ilpd.sharedlibrary.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.enums.HostelRoomStatus;
import rw.ac.ilpd.sharedlibrary.enums.RoomStatusState;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelRoomRequest
{
//    // this should be pre-existing in the system
//    private String roomNumber;
//
//    @NotNull(message = "The hostelRoomType String cannot be null")
//    private String hostelRoomType;
//
//    @NotNull(message = "The status cannot be null")
//    private String status;
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    @NotBlank(message = "Room type ID is required")
    private String roomTypeId;
}
