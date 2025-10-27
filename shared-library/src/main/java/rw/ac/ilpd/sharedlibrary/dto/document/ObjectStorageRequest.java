package rw.ac.ilpd.sharedlibrary.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ObjectStorageRequest {
    @NotBlank(message = "Bucket name is required")
    private String bucketName;
    @NotBlank(message = "Object path is required")
    private String objectPath;
    @NotNull(message = "Object  is required")
    private MultipartFile attachedFile;
}
