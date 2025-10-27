/*
* HostelRoomRequest.java
*
* Authors: Kabera Clapton(ckabera6@gmail.com)
*
* This dto is an abstraction of the HostelRoom entity.
* */
package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    @NotNull(message = "Occupation status is required")
    @Pattern(regexp = "BOOKED_GRANTED|BOOKED_NON_GRANTED|OCCUPIED|FREE", message = "Occupied status must be 'BOOKED_GRANTED', 'BOOKED_NON_GRANTED','OCCUPIED', or 'FREE'")
    private String occupationStatus;
    private String roomStatusState;

}
