package rw.ac.ilpd.paymentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.paymentservice.model.sql.Payment;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentResponse;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
//    Payment toPayment(PaymentRequest paymentRequest);
//    PaymentResponse fromPayment(Payment payment);
//    PaymentResponse toPaymentUpdate(@MappingTarget Payment payment,PaymentRequest paymentRequest);
}
