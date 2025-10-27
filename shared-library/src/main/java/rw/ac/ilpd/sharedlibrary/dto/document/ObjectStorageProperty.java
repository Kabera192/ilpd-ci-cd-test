package rw.ac.ilpd.sharedlibrary.dto.document;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
 public class ObjectStorageProperty {
     @NotBlank(message = "Bucket name is required")
     private String bucketName;
     @NotBlank(message = "Object path is required")
     private String objectPath;
}
