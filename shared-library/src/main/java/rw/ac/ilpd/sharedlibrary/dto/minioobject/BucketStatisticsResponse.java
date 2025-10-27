package rw.ac.ilpd.sharedlibrary.dto.minioobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BucketStatisticsResponse {
    private String bucket;
    private long totalSize;
    private int objectCount;
    private Instant lastModified;
}
