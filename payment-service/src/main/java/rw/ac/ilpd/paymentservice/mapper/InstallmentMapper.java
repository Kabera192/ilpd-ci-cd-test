package rw.ac.ilpd.paymentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.paymentservice.model.nosql.Installment;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.installment.InstallmentResponse;

@Mapper(componentModel = "spring")
public interface InstallmentMapper {
    Installment toInstallment(InstallmentRequest installmentRequest);
    InstallmentResponse fromInstallment(Installment installment);
    Installment toInstallmentUpdate(@MappingTarget Installment installment,InstallmentRequest installmentRequest);
}
