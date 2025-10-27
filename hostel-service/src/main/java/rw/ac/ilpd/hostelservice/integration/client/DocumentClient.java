package rw.ac.ilpd.hostelservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import java.util.List;
import java.util.Set;
@FeignClient(name = "NOTIFICATION-SERVICE",path = "/documents")
public interface DocumentClient {
    @PostMapping(consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    DocumentResponse saveDocument(@ModelAttribute ObjectStorageRequest document);
    @PostMapping(value = "/upload-object-list",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<DocumentResponse> saveDocumentList(@ModelAttribute ObjectListStorageRequest  document);
    @GetMapping("/{id}")
    DocumentResponse findById(@PathVariable String id);
    @GetMapping("")
    List<DocumentResponse> findAll();
    @GetMapping("/find-list-by-ids")
    List<DocumentResponse> findByIds(Set<String> documentIds);
    @DeleteMapping("/{id}")
    void deleteDocument(@PathVariable  String id);
}