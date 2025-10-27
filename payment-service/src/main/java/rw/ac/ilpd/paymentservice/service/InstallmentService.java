package rw.ac.ilpd.paymentservice.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.paymentservice.mapper.InstallmentMapper;
import rw.ac.ilpd.paymentservice.model.nosql.Installment;
import rw.ac.ilpd.paymentservice.repository.nosql.InstallmentRepository;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentResponse;

@Service
@RequiredArgsConstructor
public class InstallmentService {
    private final InstallmentRepository  installmentRepository;
    private  final InstallmentMapper installmentMapper;

    public InstallmentResponse createInstallment(@Valid InstallmentRequest installmentRequest) {
        Installment installment=installmentMapper.toInstallment(installmentRequest);
        Installment savedInstallment=installmentRepository.save(installment);
        return installmentMapper
                .fromInstallment(savedInstallment);
    }
}

