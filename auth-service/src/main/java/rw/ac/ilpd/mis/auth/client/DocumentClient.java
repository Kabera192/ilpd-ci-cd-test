package rw.ac.ilpd.mis.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentApi;
@FeignClient(name = "NOTIFICATION-SERVICE",path = "/documents")
public interface DocumentClient extends DocumentApi {
}
