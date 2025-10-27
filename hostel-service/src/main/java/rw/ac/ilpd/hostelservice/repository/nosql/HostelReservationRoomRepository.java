package rw.ac.ilpd.hostelservice.repository.nosql;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoom;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HostelReservationRoomRepository extends MongoRepository<HostelReservationRoom, UUID> {
    List<HostelReservationRoom> findByHostelReservationRoomTypeCountId(UUID reservationId);

    List<HostelReservationRoom> findByClientId(UUID clientId);

    List<HostelReservationRoom> findByRoomId(UUID roomId);

//    @Query("{'checkInDateTime': {$gte: ?0, $lte: ?1}}")
    List<HostelReservationRoom> findByCheckInDateTimeBetween(LocalDateTime start, LocalDateTime end);

//    @Query("{'checkOutDateTime': {$gte: ?0, $lte: ?1}}")
    List<HostelReservationRoom> findByCheckOutDateTimeBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByRandomCodeInfoFillIgnoreCase(String reservationCode);

    Optional<HostelReservationRoom> findByRandomCodeInfoFill(String reservationCode);
    @Aggregation(pipeline = {
            "{ $unwind: '$hostelReservationRoomTypeCounts' }",
            "{ $match: { 'hostelReservationRoomTypeCounts.roomTypeId': ?0 } }",
            "{ $limit: 1 }",
            "{ $replaceWith: '$hostelReservationRoomTypeCounts' }"
    })
    List<HostelReservationRoomTypeCount> findFirstRoomTypeCountByRoomTypeId(String roomTypeId);
}
