package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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