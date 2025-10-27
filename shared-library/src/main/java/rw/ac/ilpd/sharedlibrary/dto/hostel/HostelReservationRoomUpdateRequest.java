package rw.ac.ilpd.sharedlibrary.dto.hostel;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelReservationRoomUpdateRequest {
    @Positive(message = "The actualPrice cannot have a negative value")
    private BigDecimal actualPrice;
    private String checkInTime;
    private String checkOutTime;
}