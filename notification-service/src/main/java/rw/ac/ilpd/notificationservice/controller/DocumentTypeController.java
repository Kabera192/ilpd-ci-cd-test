package rw.ac.ilpd.notificationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.notificationservice.service.DocumentTypeService;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketStatisticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.ObjectDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;
import java.util.List;

@RestController()
@RequestMapping("document-types")
@RequiredArgsConstructor
public class DocumentTypeController {
    public final DocumentTypeService documentTypeService;

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> createDocumentType(
            @RequestBody @Valid DocumentTypeRequest documentTypeRequest
    ) {
        return documentTypeService.createDocumentType(documentTypeRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> findDocumentType(
            @PathVariable @NotBlank(message = "Document type can not be blank") String id
    ) {
        return documentTypeService.findDocumentType(id);
    }

    @GetMapping("")
    public PagedResponse<DocumentTypeResponse> getPagedDocumentType( @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @Parameter(description = "Page size") @RequestParam(value = "size", defaultValue = "10") int size,
                                                                    @Parameter(description = "Sort field") @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                                    @Parameter(description = "Search keyword") @RequestParam(value = "search", defaultValue = "") String search
    ){
        return documentTypeService.getPagedDocumentType(page,size,sort,search);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> delete(
            @PathVariable @NotBlank(message = "Document type can not be blank") String id
            ,@RequestBody @Valid DocumentTypeRequest documentTypeRequest
    ) {
        return documentTypeService.updateDocumentType(id, documentTypeRequest);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document by id")
    public ResponseEntity<String> deletePath(@PathVariable String id) {
        return ResponseEntity.ok(documentTypeService.deleteById(id));
    }

    @GetMapping("/{id}/bucket-properties")
    public ResponseDetailWrapper<DocumentTypeResponse, BucketDetailResponse> getBucketDetails(
            @PathVariable  String id
    ) {
        return documentTypeService.getBucketDetails(id);
    }

    @GetMapping("/{id}/bucket-objects")
    public ResponseDetailWrapper<DocumentTypeResponse,List<ObjectDetailResponse>>  getAllObjectsWithInBucket(
            @PathVariable String id
    ){
        return documentTypeService.getAllObjectWithInBucket(id);
    }

    @GetMapping("/{id}/bucket-statistics")
    public BucketStatisticsResponse getBucketStatistics(
            @PathVariable String id
    ){
        return documentTypeService.calculateBucketStats(id);
    }
}
