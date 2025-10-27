package rw.ac.ilpd.paymentservice.mapper;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.paymentservice.model.nosql.PaymentDocument;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;

@Mapper(componentModel = "spring")
public interface PaymentDocumentMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    PaymentDocument toPaymentDocument(@Valid PaymentDocumentRequest request);
}
