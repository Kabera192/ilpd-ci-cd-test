/*
 * HostelRoomImageRequest.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This dto is an abstraction of the HostelRoomImageRequest entity.
 * */
package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelRoomImageRequest
{
    @NotBlank(message = "The imageDocId cannot be null or blank")
    private String imageDocId;
}
