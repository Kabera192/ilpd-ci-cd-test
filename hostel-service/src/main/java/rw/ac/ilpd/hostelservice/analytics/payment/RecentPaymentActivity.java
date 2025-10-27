package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

//@Schema(description = "Recent payment activity")
public class RecentPaymentActivity {
//    @Schema(description = "Payment ID")
    private String paymentId;

//    @Schema(description = "Activity type", example = "PAYMENT_CREATED")
    private String activityType;

//    @Schema(description = "Payment amount", example = "450.00")
    private BigDecimal amount;

//    @Schema(description = "Payment status", example = "COMPLETED")
    private String status;

//    @Schema(description = "Activity timestamp")
    private LocalDateTime timestamp;

//    @Schema(description = "User who performed the activity")
    private String userId;
}