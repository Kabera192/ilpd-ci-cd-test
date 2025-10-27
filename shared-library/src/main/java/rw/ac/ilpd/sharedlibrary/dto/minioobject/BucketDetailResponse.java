package rw.ac.ilpd.sharedlibrary.dto.minioobject;

import lombok.*;

import java.time.ZonedDateTime;

/**
 * DTO for folder statistics
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BucketDetailResponse {
    String bucketPath;
    int objectCount;
    long totalSizeBytes;
    ZonedDateTime lastModified;
}
