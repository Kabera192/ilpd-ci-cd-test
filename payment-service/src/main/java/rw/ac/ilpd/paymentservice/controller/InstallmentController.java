package rw.ac.ilpd.paymentservice.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.ilpd.paymentservice.service.InstallmentService;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentResponse;

@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
public class InstallmentController {
    private final InstallmentService  installmentService;
    @PostMapping()
    public ResponseEntity<InstallmentResponse>createInstallment(@RequestBody @Valid InstallmentRequest installmentRequest){
        InstallmentResponse installment= installmentService.createInstallment(installmentRequest);
        return new ResponseEntity<>(installment, HttpStatus.CREATED);
    }

}
