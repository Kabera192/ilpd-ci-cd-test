package rw.ac.ilpd.sharedlibrary.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletedEvent {
    private String tableName;
    private String id;
}
