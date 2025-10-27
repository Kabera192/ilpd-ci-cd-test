package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.nosql.PaymentDocument;
import rw.ac.ilpd.sharedlibrary.enums.PaymentDocumentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentDocumentRepository extends MongoRepository<PaymentDocument, String> {
    Optional<PaymentDocument> findByPaymentId(UUID uuid);
    List<PaymentDocument> findByStatusOrderByCreatedAtDesc(PaymentDocumentStatus paymentDocumentStatus);
}
