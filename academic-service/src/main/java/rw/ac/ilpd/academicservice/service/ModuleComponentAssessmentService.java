package rw.ac.ilpd.academicservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.AssessmentAttachmentMapper;
import rw.ac.ilpd.academicservice.mapper.ModuleComponentAssessmentMapper;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AssessmentAttachment;
import rw.ac.ilpd.academicservice.model.nosql.document.ModuleComponentAssessment;
import rw.ac.ilpd.academicservice.repository.nosql.ModuleComponentAssessmentRepository;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentResponse;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleComponentAssessmentService {

    private final ModuleComponentAssessmentRepository assessmentRepository;
    private final ModuleComponentAssessmentMapper assessmentMapper;
    private final AssessmentAttachmentMapper attachmentMapper;

    private final IntakeService intakeService;
    private final ModuleService moduleService;
    private final ComponentService componentService;

    public ModuleComponentAssessmentResponse create(ModuleComponentAssessmentRequest request) {
        // Validate IDs
        validateForeignKey(request.getIntakeId(), intakeService.getEntity(request.getIntakeId()), "Intake");
        validateForeignKey(request.getModuleId(), moduleService.getEntity(request.getModuleId()), "Module");
        validateForeignKey(request.getComponentId(), componentService.getEntity(request.getComponentId()), "Component");

        // Map and save
        ModuleComponentAssessment entity = assessmentMapper.toModuleComponentAssessment(request);
        ModuleComponentAssessment saved = assessmentRepository.save(entity);
        return assessmentMapper.fromModuleComponentAssessment(saved);
    }

    public ModuleComponentAssessmentResponse getById(String id) {
        ModuleComponentAssessment assessment = getEntityById(id);
        return assessmentMapper.fromModuleComponentAssessment(assessment);
    }

    public List<ModuleComponentAssessmentResponse> getAll() {
        return assessmentRepository.findAll().stream()
                .map(assessmentMapper::fromModuleComponentAssessment)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        assessmentRepository.deleteById(id);
    }

    // ========== ATTACHMENT OPERATIONS ==========

    public AssessmentAttachmentResponse addAttachment(String assessmentId, AssessmentAttachmentRequest request) {
        ModuleComponentAssessment assessment = getEntityById(assessmentId);

        AssessmentAttachment attachment = attachmentMapper.toAssessmentAttachment(request);
        assessment.getAssessmentAttachments().add(attachment);

        assessmentRepository.save(assessment);
        return attachmentMapper.fromAssessmentAttachment(attachment);
    }

    public List<AssessmentAttachmentResponse> getAttachments(String assessmentId) {
        ModuleComponentAssessment assessment = getEntityById(assessmentId);
        return toAttachmentResponses(assessment);
    }

    public void deleteAttachment(String assessmentId, String attachmentId) {
        ModuleComponentAssessment assessment = getEntityById(assessmentId);

        boolean removed = assessment.getAssessmentAttachments().removeIf(a -> a.getId().equals(attachmentId));
        if (removed) {
            assessmentRepository.save(assessment);
        }
    }

    // ========== PRIVATE HELPERS ==========

    private ModuleComponentAssessment getEntityById(String id) {
        return getEntity(id).orElseThrow(() -> new RuntimeException("Assessment not found"));
    }

    public Optional<ModuleComponentAssessment> getEntity(String id) {
        return assessmentRepository.findById(id);
    }

    private List<AssessmentAttachmentResponse> toAttachmentResponses(ModuleComponentAssessment assessment) {
        if (assessment.getAssessmentAttachments() == null) return List.of();
        return assessment.getAssessmentAttachments().stream()
                .map(attachmentMapper::fromAssessmentAttachment)
                .collect(Collectors.toList());
    }

    private void validateForeignKey(UUID id, Optional<?> entity, String name) {
        if (entity.isEmpty() && id != null) {
            throw new IllegalArgumentException(name + " with ID " + id + " does not exist.");
        }
    }

    public boolean existsByAssessmentTypeId(UUID assessmentTypeId) {
        return assessmentRepository.existsByAssessmentTypeId(assessmentTypeId);
            }

    // =================== List of submissions given student id (give history)

    // =================== List of submissions given assessment id (this is for the prof, only give last updates, service from Claps)
}
