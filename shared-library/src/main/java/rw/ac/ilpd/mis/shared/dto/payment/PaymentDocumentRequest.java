package rw.ac.ilpd.mis.shared.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDocumentRequest {
    @NotBlank(message = "Payment id can not be Blank")
    private  String paymentId;
    @NotNull(message = "Attachment can't be Blank")
    private MultipartFile file;
    @NotNull(message = "document type is required")
    private String documentTypeId;
}
