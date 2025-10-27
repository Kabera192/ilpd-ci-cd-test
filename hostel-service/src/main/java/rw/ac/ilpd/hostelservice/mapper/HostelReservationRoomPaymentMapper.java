package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoomPayment;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.PaymentDocument;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentResponse;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HostelReservationRoomPaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HostelReservationRoomPayment toEntity(HostelReservationRoomPaymentRequest request);

    @Mapping(target = "paymentMethod", expression = "java(entity.getPaymentMethod().name())")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)")
    HostelReservationRoomPaymentResponse toResponse(HostelReservationRoomPayment entity);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "reservationRoomPaymentId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaymentDocument toPaymentDocumentEntity(PaymentDocumentRequest request);

    PaymentDocumentResponse toPaymentDocumentResponse(PaymentDocument document);

    List<PaymentDocument> toPaymentDocumentEntities(List<PaymentDocumentRequest> requests);
    List<PaymentDocumentResponse> toPaymentDocumentResponses(List<PaymentDocument> documents);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HostelReservationRoomPayment updateEntityFromRequest(HostelReservationRoomPaymentRequest request,
                                 @MappingTarget HostelReservationRoomPayment entity);
}