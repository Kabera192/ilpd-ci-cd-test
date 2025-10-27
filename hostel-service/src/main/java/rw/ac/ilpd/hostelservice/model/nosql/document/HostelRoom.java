package rw.ac.ilpd.hostelservice.model.nosql.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.HostelRoomStatus;
import rw.ac.ilpd.sharedlibrary.enums.RoomStatusState;

import java.time.LocalDateTime;

@Document(collection = "hostel_hostel_rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HostelRoom {
    @Id
    private String id;
    private String roomNumber;
    /*Reference to room type document*/
    private String roomTypeId;
    private HostelRoomStatus occupationStatus;
    private RoomStatusState roomStatusState;
    @CreatedDate
    private LocalDateTime createdAt;

}
