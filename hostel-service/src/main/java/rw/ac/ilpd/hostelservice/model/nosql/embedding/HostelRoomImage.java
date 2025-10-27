package rw.ac.ilpd.hostelservice.model.nosql.embedding;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HostelRoomImage {
    @Builder.Default
    private String id= UUID.randomUUID().toString();
    private String imageDocId;
}
