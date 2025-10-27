package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ProhibitedTransferCoupleService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ProhibitedTransferCouplePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleResponse;

import java.util.List;

@RestController
@RequestMapping(ProhibitedTransferCouplePriv.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Prohibited Transfer Couple Apis", description = "Endpoints for managing prohibited transfer couples")
public class ProhibitedTransferCoupleController {
    private final ProhibitedTransferCoupleService prohibitedTransferCoupleService;
    /** Creates a new prohibited transfer couple. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.CREATE})
    @PostMapping(ProhibitedTransferCouplePriv.CREATE_PATH)
    @Operation(summary = "Create a new prohibited transfer couple",
            description = "Creates a new prohibited transfer couple.")
    public ResponseEntity<ProhibitedTransferCoupleResponse> createProhibitedTransferCouple(
             @RequestBody @Valid ProhibitedTransferCoupleRequest request) {
        return prohibitedTransferCoupleService.createProhibitedTransferCouple(request);
    }

    /** Retrieves a prohibited transfer couple by its unique ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.READ})
    @GetMapping(ProhibitedTransferCouplePriv.READ_PATH)
    @Operation(summary = "Get a prohibited transfer couple by ID",
            description = "Retrieves a prohibited transfer couple by its unique ID.")
    public ResponseEntity<ProhibitedTransferCoupleResponse> getProhibitedTransferCoupleById(
            @PathVariable String id) {
        return prohibitedTransferCoupleService.getProhibitedTransferCoupleById(id);
    }

    /** Retrieves all prohibited transfer couples. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.READ_ALL})
    @GetMapping(ProhibitedTransferCouplePriv.READ_ALL_PATH)
    @Operation(summary = "Get all prohibited transfer couples",
            description = "Retrieves a list of all prohibited transfer couples.")
    public ResponseEntity<List<ProhibitedTransferCoupleResponse>> getAllProhibitedTransferCouples() {
        return prohibitedTransferCoupleService.getAllProhibitedTransferCouples(false);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.READ_ALL_ARCHIVE})
    @GetMapping(ProhibitedTransferCouplePriv.READ_ALL_ARCHIVE_PATH)
    @Operation(summary = "Get all prohibited transfer couples",
            description = "Retrieves a list of all prohibited transfer couples.")
    public ResponseEntity<List<ProhibitedTransferCoupleResponse>> getAllArchivedProhibitedTransferCouples() {
        return prohibitedTransferCoupleService.getAllProhibitedTransferCouples(true);
    }
    /** Updates an existing prohibited transfer couple by its unique ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.UPDATE})
    @PutMapping(ProhibitedTransferCouplePriv.UPDATE_PATH)
    @Operation(summary = "Update a prohibited transfer couple",
            description = "Updates an existing prohibited transfer couple by its unique ID.")
    public ResponseEntity<ProhibitedTransferCoupleResponse> updateProhibitedTransferCouple(
            @PathVariable String id, @Valid @RequestBody ProhibitedTransferCoupleRequest request) {
        return prohibitedTransferCoupleService.updateProhibitedTransferCouple(id, request);
    }
    /** Updates an existing prohibited transfer couple by its unique ID and delete status. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.UPDATE_DELETE_STATUS})
    @PatchMapping(ProhibitedTransferCouplePriv.UPDATE_DELETE_STATUS_PATH)
    @Operation(summary = "Update a prohibited transfer delete status",
            description = "Updates an existing prohibited transfer couple by its unique ID and delete status.")
    public ResponseEntity<ProhibitedTransferCoupleResponse> updateDeleteStatusOfProhibitedTransferCouple(
            @PathVariable String id, @RequestParam boolean delete) {
        return prohibitedTransferCoupleService.updateDeleteStatusOfProhibitedTransferCouple(id, delete);
    }
    /** Deletes an existing prohibited transfer couple by its unique ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, ProhibitedTransferCouplePriv.DELETE})
    @DeleteMapping(ProhibitedTransferCouplePriv.DELETE_PATH)
    @Operation(summary = "Delete a prohibited transfer couple",
            description = "Deletes an existing prohibited transfer couple by its unique ID.")
    public ResponseEntity<String> deleteProhibitedTransferCouple(@PathVariable String id) {
        ResponseEntity<ProhibitedTransferCoupleResponse> response = prohibitedTransferCoupleService.updateDeleteStatusOfProhibitedTransferCouple(id, true);
        return ResponseEntity.ok("Prohibited transfer couple deleted successfully.");
    }
}