package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDoc;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocEmbed;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocResponse;

@Component
public class IntakeApplicationRequiredDocMapper {

    public IntakeApplicationRequiredDoc toIntakeApplicationRequiredDoc(
            Intake intake,
            IntakeApplicationRequiredDocName intakeApplicationRequiredDocName,
            boolean isRequired
    ) {
        return IntakeApplicationRequiredDoc.builder()
                .intake(intake)
                .documentRequiredName(intakeApplicationRequiredDocName)
                .isRequired(isRequired)
                .build();
    }

    public IntakeApplicationRequiredDocResponse fromIntakeApplicationRequiredDoc(
            IntakeApplicationRequiredDoc entity
    ) {
        return IntakeApplicationRequiredDocResponse.builder()
                .id(entity.getId().toString())
                .intakeId(entity.getIntake().getId().toString())
                .requiredDocNameId(entity.getDocumentRequiredName().getId().toString())
                .isRequired(Boolean.TRUE.equals(entity.getIsRequired()))
                .build();
    }

    public IntakeApplicationRequiredDocResponse fromIntakeApplicationRequiredDocRequest(
            IntakeApplicationRequiredDocRequest request,
            IntakeApplicationRequiredDocEmbed embed
    )
    {
        return IntakeApplicationRequiredDocResponse.builder()
                .intakeId(request.getIntakeId())
                .requiredDocNameId(embed.getRequiredDocNameId())
                .isRequired(embed.isRequired())
                .build();
    }
}
