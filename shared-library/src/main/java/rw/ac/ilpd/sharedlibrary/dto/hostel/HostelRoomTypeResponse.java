/*
 * HostelRoomTypeResponse.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This dto is an abstraction of the HostelRoomTypeResponse entity.
 * */
package rw.ac.ilpd.sharedlibrary.dto.hostel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelRoomTypeResponse 
{
    private String id;

    private String type;

    private int maxCapacity;

    private List<HostelRoomImageResponse> hostelRoomImages;

    private List<HostelRoomTypePricingResponse> hostelRoomPrices;
}
