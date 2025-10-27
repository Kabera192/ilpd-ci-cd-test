package rw.ac.ilpd.hostelservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoom;
import rw.ac.ilpd.sharedlibrary.enums.RoomStatusState;

import java.util.Optional;

@Repository
public interface HostelRoomRepository  extends MongoRepository<HostelRoom, String> {
    Optional<HostelRoom> findByRoomNumberAndRoomStatusState(String roomNumber, RoomStatusState roomStatusState);
}
