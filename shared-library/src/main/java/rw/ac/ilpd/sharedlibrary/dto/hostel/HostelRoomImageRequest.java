/*
 * HostelRoomImageRequest.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This dto is an abstraction of the HostelRoomImageRequest entity.
 * */
package rw.ac.ilpd.sharedlibrary.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelRoomImageRequest
{
    @NotBlank(message = "The roomType String cannot be null or blank")
    @RestrictedString
    private String roomType;

    @NotBlank(message = "The imageDocId cannot be null or blank")
    @RestrictedString
    private String imageDocId;
}
