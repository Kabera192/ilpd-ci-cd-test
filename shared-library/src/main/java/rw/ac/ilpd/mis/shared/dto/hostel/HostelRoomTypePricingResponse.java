package rw.ac.ilpd.mis.shared.dto.hostel;

import lombok.*;
import rw.ac.ilpd.mis.shared.enums.ClientType;
import rw.ac.ilpd.mis.shared.enums.PaymentPeriod;
import rw.ac.ilpd.mis.shared.enums.PricingStatus;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HostelRoomTypePricingResponse {
    private String id;

    private String roomTypeId;

    private int capacity;

    private BigDecimal price;

    private PaymentPeriod paymentPeriod;

    private ClientType clientType;

    private PricingStatus pricingStatus;

    private String description;

    private String createdAt;
}
