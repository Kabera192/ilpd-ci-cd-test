package rw.ac.ilpd.academicservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.document.ModuleComponentAssessment;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentResponse;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentResponse;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentMode;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ModuleComponentAssessmentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final AssessmentAttachmentMapper assessmentAttachmentMapper;

    public ModuleComponentAssessment toModuleComponentAssessment(ModuleComponentAssessmentRequest request) {
        return ModuleComponentAssessment.builder()
                .componentId(request.getComponentId())
                .moduleId(request.getModuleId())
                .intakeId(request.getIntakeId())
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .assessmentTypeId(request.getAssessmentTypeId())
                .status(request.getStatus())
                .evaluationFormId(request.getEvaluationFormsId() == null ? null : request.getEvaluationFormsId())
                .mode(request.getMode())
                .maxGrade(request.getMaxGrade().doubleValue())
                .build();
    }

    public ModuleComponentAssessmentResponse fromModuleComponentAssessment(ModuleComponentAssessment assessment) {
        return ModuleComponentAssessmentResponse.builder()
                .id(assessment.getId())
                .componentId(assessment.getComponentId().toString())
                .moduleId(assessment.getModuleId().toString())
                .intakeId(assessment.getIntakeId().toString())
                .title(assessment.getTitle())
                .description(assessment.getDescription())
                .postedBy(assessment.getPostedById() == null ? null : assessment.getPostedById().toString())
                .dueDate(assessment.getDueDate() != null ? assessment.getDueDate() : null)
                .assessmentTypeId(assessment.getAssessmentTypeId().toString())
                .status(assessment.getStatus().name())
                .evaluationFormsId(assessment.getEvaluationFormId() == null ? null : assessment.getEvaluationFormId().toString())
                .mode(assessment.getMode().name())
                .createdAt(assessment.getCreatedAt() != null ? assessment.getCreatedAt().format(FORMATTER) : null)
                .updatedAt(assessment.getUpdatedAt() != null ? assessment.getUpdatedAt().format(FORMATTER) : null)
                .attachmentResponses(assessment.getAssessmentAttachments() != null ?
                        assessment.getAssessmentAttachments().stream()
                                .map(assessmentAttachmentMapper::fromAssessmentAttachment).toList() : null)
                .maxGrade(BigDecimal.valueOf(assessment.getMaxGrade()))
                .build();
    }
}
