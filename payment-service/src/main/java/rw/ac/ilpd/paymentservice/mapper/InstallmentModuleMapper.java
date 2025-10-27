package rw.ac.ilpd.paymentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.paymentservice.model.nosql.InstallmentModule;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.installmentmodule.InstallmentModuleResponse;

@Mapper(componentModel = "spring")
public interface InstallmentModuleMapper {
    InstallmentModule toInstallmentModule(InstallmentModuleRequest request);
    InstallmentModuleResponse fromInstallmentModule(InstallmentModule installmentModule);
    InstallmentModule toInstallmentModuleUpdate(@MappingTarget InstallmentModule installmentModule,InstallmentModuleRequest request);
}
