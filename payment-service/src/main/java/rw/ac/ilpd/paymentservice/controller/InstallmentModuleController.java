package rw.ac.ilpd.paymentservice.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.paymentservice.service.InstallmentModuleService;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleResponse;

@RestController
@RequestMapping("/installment-modules")
@RequiredArgsConstructor
public class InstallmentModuleController {
    private final InstallmentModuleService installmentModuleService;
    @PostMapping
    public  ResponseEntity<InstallmentModuleResponse> createInstallmentModule(@RequestBody @Valid InstallmentModuleRequest installmentModuleRequest){
        InstallmentModuleResponse imr=installmentModuleService.createInstallmentModule(installmentModuleRequest);
        return new ResponseEntity<>(imr, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<InstallmentModuleResponse> updateInstallmentModule(@PathVariable String id, @RequestBody InstallmentModuleRequest installmentModuleRequest){
        InstallmentModuleResponse imr=installmentModuleService.updateInstallmentModule(id,installmentModuleRequest);
        return new ResponseEntity<>(imr, HttpStatus.CREATED);
    }

}
