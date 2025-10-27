package rw.ac.ilpd.reportingservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormUserFiller;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormUserFillerRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormUserFillerResponse;

import java.util.UUID;

@Component
public class EvaluationFormUserFillerMapper {

    public EvaluationFormUserFiller fromEvaluationFormUserFillerRequest(EvaluationFormUserFillerRequest request) {
        if (request == null) return null;

        return EvaluationFormUserFiller.builder()
                .id(UUID.randomUUID().toString())
                .level(request.getLevel())
                .levelId(request.getLevelId())
                .roleId(request.getRoleId())
                .intakeId(request.getIntakeId())
                .build();
    }

    public EvaluationFormUserFillerResponse toEvaluationFormUserFillerResponse(EvaluationFormUserFiller filler) {
        if (filler == null) return null;

        return EvaluationFormUserFillerResponse.builder()
                .id(filler.getId())
                .level(filler.getLevel() != null ? filler.getLevel().name() : null)
                .levelId(filler.getLevelId() != null ? filler.getLevelId().toString() : null)
                .roleId(filler.getRoleId() != null ? filler.getRoleId().toString() : null)
                .intakeId(filler.getIntakeId() != null ? filler.getIntakeId().toString() : null)
                .build();
    }
}
