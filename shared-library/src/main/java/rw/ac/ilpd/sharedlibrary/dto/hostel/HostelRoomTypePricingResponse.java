package rw.ac.ilpd.sharedlibrary.dto.hostel;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.ClientType;
import rw.ac.ilpd.sharedlibrary.enums.PaymentPeriod;
import rw.ac.ilpd.sharedlibrary.enums.PricingStatus;

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
