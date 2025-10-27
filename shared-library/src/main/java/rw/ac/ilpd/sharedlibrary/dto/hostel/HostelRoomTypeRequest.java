/*
 * HostelRoomTypeRequest.java
 *
 * Authors: Kabera Clapton(ckabera6@gmail.com)
 *
 * This dto is an abstraction of the HostelRoomTypeRequest entity.
 * */
package rw.ac.ilpd.sharedlibrary.dto.hostel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelRoomTypeRequest
{
    // these should be pre-defined in the system somehow!
    @NotBlank(message = "Room type is required")
    @Pattern(regexp = "^(Standard|VIP_SMALL|VIP_LARGE|VVIP_SMALL|VVIP_LARGE)$",
            message = "Type must be one of: Standard, VIP_SMALL, VIP_LARGE, VVIP_SMALL, VVIP_LARGE")
    private String type;
//
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 10, message = "Capacity cannot exceed 10")
    @Positive(message = "The capacity cannot have a negative value")
    private int maxCapacity;
//
//    @NotNull(message = "Price is required")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
//    private BigDecimal price;
//
//    @NotNull(message = "The description is required")
//    private String description;
    List<MultipartFile> images;
//    private List<HostelRoomImageRequest> hostelRoomImages;
//    private List<HostelRoomTypePricingRequest> hostelRoomPrices;
}
