package rw.ac.ilpd.paymentservice.integration.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;

@FeignClient(name = "NOTIFICATION-SERVICE",path = "/documents")
public interface DocumentClient {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DocumentResponse createPaymentDocument(@ModelAttribute  @Valid DocumentRequest documentRequest);
}
