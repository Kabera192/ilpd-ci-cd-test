package rw.ac.ilpd.sharedlibrary.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadObjectRequest {
    private MultipartFile multipartFile;
}
