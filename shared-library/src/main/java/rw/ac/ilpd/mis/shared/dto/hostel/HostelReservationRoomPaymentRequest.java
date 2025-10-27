/*
* HostelReservationRoomPaymentRequest.java
*
* Authors: Kabera Clapton(ckabera6@gmail.com)
*
* This dto is an abstraction of the HostelReservationRoomPaymentRequest entity
* */
package rw.ac.ilpd.mis.shared.dto.hostel;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.enums.PaymentMethod;
import rw.ac.ilpd.mis.shared.enums.PaymentStatus;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelReservationRoomPaymentRequest
{
//    @NotNull(message = "The reservedRoom String cannot be null")
//    private String reservedRoom;
//
//    @NotNull(message = "The amount cannot be null")
//    @Positive(message = "The amount cannot be a negative value")
//    private Double amount;
//
//    private String transactionNumber;
//
//    // References a setting account
//    @NotNull(message = "credit account number cannot be null")
//    private String creditAccount;
//
//    @NotNull(message = "Proof of Payment doc cannot be null")
//    private String proofOfPaymentDoc;
//
//    @NotNull(message = "The payment method cannot be null")
//    private String paymentMethod;
@NotNull(message = "Payment amount is required")
@DecimalMin(value = "0.01", message = "Payment amount must be greater than 0", inclusive = true)
@DecimalMax(value = "999999.99", message = "Payment amount exceeds maximum limit", inclusive = true)
@Digits(integer = 6, fraction = 2, message = "Amount must have maximum 6 integer digits and 2 decimal places")
private BigDecimal amount;

//    @NotBlank(message = "Transaction number is required")
//    @Size(min = 5, max = 100, message = "Transaction number must be between 5 and 100 characters")
//    @Pattern(regexp = "^[A-Za-z0-9\\-_]+$", message = "Transaction number can only contain letters, numbers, hyphens, and underscores")
    private String transactionNumber;

    @NotNull(message = "Credit account ID is required")
    private String creditAccountId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod= PaymentMethod.ONLINE;

    private PaymentStatus paymentStatus = PaymentStatus.NOT_COMPLETE;
}
