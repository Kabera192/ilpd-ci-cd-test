package rw.ac.ilpd.mis.shared.dto.coursematerial;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.mis.shared.dto.document.DocumentRequest;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseMaterialDocumentRequest{
    @NotNull(message = "Document is required")
    private DocumentRequest document;
    @NotNull(message = "Course material is required")
    private CourseMaterialRequest courseMaterial;
     List<MultipartFile> files;
}
