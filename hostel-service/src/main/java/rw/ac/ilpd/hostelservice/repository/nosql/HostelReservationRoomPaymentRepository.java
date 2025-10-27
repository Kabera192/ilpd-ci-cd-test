package rw.ac.ilpd.hostelservice.repository.nosql;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoomPayment;
import rw.ac.ilpd.hostelservice.projection.MonthlyStatsProjection;
import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HostelReservationRoomPaymentRepository extends MongoRepository<HostelReservationRoomPayment, String> {
    List<HostelReservationRoomPayment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<HostelReservationRoomPayment> findByPaymentMethod(PaymentMethod paymentMethod);

    Optional<HostelReservationRoomPayment> findByTransactionNumber(String transactionNumber);

    List<HostelReservationRoomPayment> findByCreditAccountId(UUID creditAccountId);

    @Query("{'amount': {$gte: ?0, $lte: ?1}}")
    List<HostelReservationRoomPayment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    @Query("{'createdAt': {$gte: ?0, $lte: ?1}}")
    List<HostelReservationRoomPayment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    long countByPaymentStatus(PaymentStatus paymentStatus);

    @Aggregation(pipeline = {
            "{ $match: { createdAt: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: { year: { $year: '$createdAt' }, month: { $month: '$createdAt' } }, " +
                    "count: { $sum: 1 }, totalAmount: { $sum: '$amount' }, " +
                    "completedCount: { $sum: { $cond: [{ $eq: ['$paymentStatus', 'COMPLETED'] }, 1, 0] } }, " +
                    "completedAmount: { $sum: { $cond: [{ $eq: ['$paymentStatus', 'COMPLETED'] }, '$amount', 0] } } } }",
            "{ $sort: { '_id.year': 1, '_id.month': 1 } }"
    })
    List<MonthlyStatsProjection> getMonthlyStats(LocalDateTime startDate, LocalDateTime endDate);
//New
@Query("{'createdAt': {$gte: ?0, $lte: ?1}}")
List<HostelReservationRoomPayment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'amount': {$gte: ?0}}")
    List<HostelReservationRoomPayment> findHighValuePayments(BigDecimal threshold);

//    @Aggregation(pipeline = {
//            "{ $match: { 'paymentStatus': ?0 } }",
//            "{ $group: { _id: null, total: { $sum: '$amount' }, count: { $sum: 1 } } }"
//    })
//    PaymentAggregationResult getPaymentStatsByStatus(PaymentStatus status);
//
//    @Aggregation(pipeline = {
//            "{ $group: { _id: '$paymentMethod', total: { $sum: '$amount' }, count: { $sum: 1 } } }"
//    })
//    List<PaymentMethodAggregation> getPaymentStatsByMethod();
//
//    @Query("{'createdAt': {$gte: ?0}, 'paymentStatus': 'COMPLETED'}")
//    List<HostelReservationRoomPayment> findCompletedPaymentsSince(LocalDateTime since);
}
