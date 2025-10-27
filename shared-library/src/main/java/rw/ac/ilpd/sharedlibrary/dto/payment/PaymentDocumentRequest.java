package rw.ac.ilpd.sharedlibrary.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDocumentRequest {
    @NotBlank(message = "Payment id can not be Blank")
    @RestrictedString
    private  String paymentId;
    @NotNull(message = "Attachment can't be Blank")
    private MultipartFile file;
    @NotNull(message = "document type is required")
    private String documentTypeId;
}
