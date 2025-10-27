/*
* HostelReservationRoomPaymentRequest.java
*
* Authors: Kabera Clapton(ckabera6@gmail.com)
*
* This dto is an abstraction of the HostelReservationRoomPaymentRequest entity
* */
package rw.ac.ilpd.sharedlibrary.dto.hostel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentResponse;
import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostelReservationRoomPaymentRequest
{
    @NotBlank(message = "The reservedRoom String cannot be null or blank")
    @RestrictedString
    private String reservedRoom;
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

    @RestrictedString
    private String transactionNumber;

    // References a setting account
    @NotBlank(message = "credit account number cannot be null or blank")
    @RestrictedString
    private String creditAccount;
    @NotNull(message = "Credit account ID is required")
    private String creditAccountId;

    @NotBlank(message = "Proof of Payment doc cannot be null or blank")
    @RestrictedString
    private String proofOfPaymentDoc;
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod=PaymentMethod.ONLINE;

    private PaymentStatus paymentStatus = PaymentStatus.NOT_COMPLETE;
}
