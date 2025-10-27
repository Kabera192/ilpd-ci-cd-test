package rw.ac.ilpd.reportingservice.model.nosql.document;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "report_user_positions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserPosition {
    @Id
    private String id;
    private UUID userId;
    private String  positionId;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime quiteDate;
}
