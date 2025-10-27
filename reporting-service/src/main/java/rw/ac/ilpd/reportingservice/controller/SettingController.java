package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.reportingservice.service.SettingService;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingApi;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingUpdateRequest;
import rw.ac.ilpd.sharedlibrary.enums.SettingCategories;

import java.util.List;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SettingController implements SettingApi {

    private final SettingService service;
    @Operation(summary = "Get setting by ID", description = "Retrieves a setting by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Setting not found")
    })
    @Override
    public ResponseEntity<SettingResponse> getById(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("GET /api/v1/settings/{} - Fetching setting", id);

        SettingResponse response = service.getById(id);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<List<SettingResponse>> getAllBankAccount(String activeStatus, String search) {
        List<SettingResponse> response = service.getSettingByCategory(SettingCategories.BANK,activeStatus,search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SettingResponse>> getAllAcademicSettings(String activeStatus, String search) {
        List<SettingResponse> response = service.getSettingByCategory(SettingCategories.ACADEMIC,activeStatus,search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SettingResponse>> getAllNotificationSettings(String activeStatus, String search) {
        List<SettingResponse> response = service.getSettingByCategory(SettingCategories.NOTIFICATION,activeStatus,search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SettingResponse>> getAllFinancialSettings(String activeStatus, String search) {
        List<SettingResponse> response = service.getSettingByCategory(SettingCategories.FINANCE,activeStatus,search);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update setting", description = "Updates an existing setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Setting not found")
    })
     @Override
    public ResponseEntity<SettingResponse> update(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id,
            @Valid @RequestBody SettingUpdateRequest request) {

        log.info("PUT /api/v1/settings/{} - Updating setting", id);

        SettingResponse response = service.update(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete setting", description = "Hard deletes a setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Setting not found")
    })
    @Override
    public ResponseEntity<Void> delete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("DELETE /api/v1/settings/{} - Deleting setting", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Restore setting", description = "Restores a soft deleted setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully restored"),
            @ApiResponse(responseCode = "404", description = "Setting not found"),
            @ApiResponse(responseCode = "409", description = "Key conflict with existing active setting")
    })
    @Override
    public ResponseEntity<String> restore(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("PATCH /api/v1/settings/{}/restore - Restoring setting", id);

        service.restore(id);

        return ResponseEntity.ok("Setting restored successfully");
    }


    @Operation(summary = "Get all active settings as list", description = "Retrieves all active settings as a simple list")
    @Override
    public ResponseEntity<List<SettingResponse>> getAllActiveList() {

        log.info("GET /api/v1/settings/list - Fetching all active settings as list");

        List<SettingResponse> response = service.getAllActiveList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get count of active settings", description = "Returns the count of active settings")
    @Override
    public ResponseEntity<Long> countActive() {

        log.info("GET /api/v1/settings/count/active - Getting count of active settings");

        long count = service.countActive();

        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Get count of deleted settings", description = "Returns the count of deleted settings")
    @Override
    public ResponseEntity<Long> countDeleted() {

        log.info("GET /api/v1/settings/count/deleted - Getting count of deleted settings");

        long count = service.countDeleted();

        return ResponseEntity.ok(count);
    }
}
