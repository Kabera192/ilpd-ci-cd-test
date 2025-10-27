package rw.ac.ilpd.hostelservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservation;
import rw.ac.ilpd.sharedlibrary.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HostelReservationRepository  extends MongoRepository<HostelReservation, String> {
    List<HostelReservation> findByReserverPersonId(String reserverPersonId);
    List<HostelReservation> findByReservationStatus(ReservationStatus status);
    Optional<HostelReservation> findByRandomQueryCode(String randomQueryCode);
    @Query("{'arrivalDate': {$gte: ?0, $lte: ?1}}")
    List<HostelReservation> findByArrivalDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("{'id': ?0, 'hostelReservationRoomTypeCounts.id': ?1}")
    Optional<HostelReservation> findByIdAndRoomTypeCountId(String reservationId, String roomTypeCountId);
}
