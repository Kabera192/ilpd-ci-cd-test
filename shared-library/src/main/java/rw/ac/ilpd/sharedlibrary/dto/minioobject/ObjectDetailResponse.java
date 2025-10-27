package rw.ac.ilpd.sharedlibrary.dto.minioobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ObjectDetailResponse {
    private String name;
    private long size;
    private String lastModified;

}
