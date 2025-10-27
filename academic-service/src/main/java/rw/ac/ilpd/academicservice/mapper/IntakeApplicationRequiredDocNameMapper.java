package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameResponse;

@Component
public class IntakeApplicationRequiredDocNameMapper {
    public IntakeApplicationRequiredDocName toIntakeApplicationRequiredDocName(
            IntakeApplicationRequiredDocNameRequest request
    ){
        return IntakeApplicationRequiredDocName
                .builder()
                .name(request.getName())
                .build();
    }

    public IntakeApplicationRequiredDocNameResponse fromIntakeApplicationRequiredDocName(
            IntakeApplicationRequiredDocName requiredDocName
    ){
        return IntakeApplicationRequiredDocNameResponse
                .builder()
                .id(requiredDocName.getId().toString())
                .name(requiredDocName.getName())
                .build();
    }
}
