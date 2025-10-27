package rw.ac.ilpd.academicservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentApi;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import java.util.List;
import java.util.Set;
@FeignClient(name = "NOTIFICATION-SERVICE",path = "/documents")
public interface DocumentClient extends DocumentApi {
}
