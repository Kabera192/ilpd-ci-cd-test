package rw.ac.ilpd.paymentservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.paymentservice.mapper.InstallmentModuleMapper;
import rw.ac.ilpd.paymentservice.model.nosql.InstallmentModule;
import rw.ac.ilpd.paymentservice.repository.nosql.InstallmentModuleRepository;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleResponse;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstallmentModuleService {
    private final InstallmentModuleRepository installmentModuleRepository;
    private final InstallmentModuleMapper installmentModuleMapper;

    public InstallmentModuleResponse createInstallmentModule(@Valid InstallmentModuleRequest installmentModuleRequest) {
//        find whether the curriculum is exist in installment module before creating it
        findByCurriculumModuleId(installmentModuleRequest.getCurriculumModuleId()).stream().findFirst()
                .ifPresent(installmentModule -> {throw new EntityExistsException("Installment module already exists");
                });
        InstallmentModule installmentModule=installmentModuleMapper.toInstallmentModule(installmentModuleRequest);
        InstallmentModule installmentModuleSaved=installmentModuleRepository.save(installmentModule);
        return installmentModuleMapper.fromInstallmentModule(installmentModuleSaved);
    }
    public Optional<InstallmentModule>getEntity(String id){
        return installmentModuleRepository.findById(id);
    }
    public Optional<InstallmentModule>findByCurriculumModuleId(
            String curriculumModuleId
    ){
        return installmentModuleRepository.findByCurriculumModuleId(UUID.fromString(curriculumModuleId));
    }
//Update Installment
    public InstallmentModuleResponse updateInstallmentModule(
            @NotBlank(message = "Can't find installment module")String id,
            @Valid InstallmentModuleRequest installmentModuleRequest
    ) {
     InstallmentModule installmentModuleStream = getEntity(id).stream().map(installmentModule -> {
            InstallmentModule im = installmentModuleMapper.toInstallmentModuleUpdate(installmentModule, installmentModuleRequest);
            return installmentModuleRepository.save(im);
        }).findFirst()
             .orElseThrow(()->new EntityNotFoundException("Installment module not found"));
     return installmentModuleMapper.fromInstallmentModule(installmentModuleStream);
    }

}
