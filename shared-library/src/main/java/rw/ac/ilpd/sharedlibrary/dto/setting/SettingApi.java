package rw.ac.ilpd.sharedlibrary.dto.setting;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SettingApi {
    @GetMapping("/{id}")
     ResponseEntity<SettingResponse> getById(@PathVariable @NotBlank(message = "ID cannot be blank") String id);

    @GetMapping("/bank-account")
    ResponseEntity<List<SettingResponse>> getAllBankAccount(
            @RequestParam(name = "active-status",defaultValue = "active") @NotBlank(message = "Key cannot be blank") String activeStatus,
            @RequestParam(name = "search",defaultValue = "",required = false)String search
    );
    @GetMapping("/academics")
    ResponseEntity<List<SettingResponse>> getAllAcademicSettings(
            @RequestParam(name = "active-status",defaultValue = "active") @NotBlank(message = "Key cannot be blank") String activeStatus,
            @RequestParam(name = "search",defaultValue = "",required = false)String search
    );
    @GetMapping("/notifications")
    ResponseEntity<List<SettingResponse>> getAllNotificationSettings(
            @RequestParam(name = "active-status",defaultValue = "active") @NotBlank(message = "Key cannot be blank") String activeStatus,
            @RequestParam(name = "search",defaultValue = "",required = false)String search
    );
    @GetMapping("/finance")
    ResponseEntity<List<SettingResponse>> getAllFinancialSettings(
            @RequestParam(name = "active-status",defaultValue = "active") @NotBlank(message = "Key cannot be blank") String activeStatus,
            @RequestParam(name = "search",defaultValue = "",required = false)String search
    );
//    end of get
    @PutMapping("/{id}")
    ResponseEntity<SettingResponse> update(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id, @Valid @RequestBody SettingUpdateRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id);

    @PatchMapping("/{id}/restore")
    ResponseEntity<String> restore(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id);

    @GetMapping("/list")
    ResponseEntity<List<SettingResponse>> getAllActiveList() ;

    @GetMapping("/count/active")
    ResponseEntity<Long> countActive();

    @GetMapping("/count/deleted")
    ResponseEntity<Long> countDeleted();
}
