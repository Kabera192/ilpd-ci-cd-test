package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AssessmentAttachment;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentResponse;

@Component
public class AssessmentAttachmentMapper {

    public AssessmentAttachment toAssessmentAttachment(AssessmentAttachmentRequest request) {
        AssessmentAttachment attachment = new AssessmentAttachment();
        attachment.setModuleComponentAssessmentId(request.getModuleComponentAssessmentId());
        attachment.setDocumentId(request.getDocumentId());
        // ID is auto-generated in the model's default value (UUID.randomUUID())
        return attachment;
    }

    public AssessmentAttachmentResponse fromAssessmentAttachment(AssessmentAttachment attachment) {
        return AssessmentAttachmentResponse.builder()
                .id(attachment.getId())
                .moduleComponentAssessmentId(attachment.getModuleComponentAssessmentId())
                .documentId(attachment.getDocumentId())
                .build();
    }
}
