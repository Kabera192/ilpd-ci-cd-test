package rw.ac.ilpd.sharedlibrary.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ObjectListStorageRequest {
    @NotBlank(message = "Bucket name is required")
    private String bucketName;
    @NotBlank(message = "Object path is required")
    private String objectPath;
    @NotNull(message = "File to upload is required")
    private List<MultipartFile> attachedFiles;
}
