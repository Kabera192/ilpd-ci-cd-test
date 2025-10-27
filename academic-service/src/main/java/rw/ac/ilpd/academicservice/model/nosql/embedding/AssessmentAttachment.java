package rw.ac.ilpd.academicservice.model.nosql.embedding;

import java.util.UUID;

/**
 * This entity maps a particular module component assessment to a document
 * that has been attached by the lecturer to that assessment. Note that an
 * assessment can have many attached documents.
 */

public class AssessmentAttachment {
    private String id = UUID.randomUUID().toString();

    private String moduleComponentAssessmentId;

    private String documentId;


    public AssessmentAttachment(String id, String moduleComponentAssessmentId, String documentId) {
        this.id = id;
        this.moduleComponentAssessmentId = moduleComponentAssessmentId;
        this.documentId = documentId;
    }

    public AssessmentAttachment() {
    }

    public static AssessmentAttachmentBuilder builder() {
        return new AssessmentAttachmentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getModuleComponentAssessmentId() {
        return this.moduleComponentAssessmentId;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModuleComponentAssessmentId(String moduleComponentAssessmentId) {
        this.moduleComponentAssessmentId = moduleComponentAssessmentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AssessmentAttachment)) return false;
        final AssessmentAttachment other = (AssessmentAttachment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$moduleComponentAssessmentId = this.getModuleComponentAssessmentId();
        final Object other$moduleComponentAssessmentId = other.getModuleComponentAssessmentId();
        if (this$moduleComponentAssessmentId == null ? other$moduleComponentAssessmentId != null : !this$moduleComponentAssessmentId.equals(other$moduleComponentAssessmentId))
            return false;
        final Object this$documentId = this.getDocumentId();
        final Object other$documentId = other.getDocumentId();
        if (this$documentId == null ? other$documentId != null : !this$documentId.equals(other$documentId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AssessmentAttachment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $moduleComponentAssessmentId = this.getModuleComponentAssessmentId();
        result = result * PRIME + ($moduleComponentAssessmentId == null ? 43 : $moduleComponentAssessmentId.hashCode());
        final Object $documentId = this.getDocumentId();
        result = result * PRIME + ($documentId == null ? 43 : $documentId.hashCode());
        return result;
    }

    public String toString() {
        return "AssessmentAttachment(id=" + this.getId() + ", moduleComponentAssessmentId=" + this.getModuleComponentAssessmentId() + ", documentId=" + this.getDocumentId() + ")";
    }

    public static class AssessmentAttachmentBuilder {
        private String id;
        private String moduleComponentAssessmentId;
        private String documentId;

        AssessmentAttachmentBuilder() {
        }

        public AssessmentAttachmentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AssessmentAttachmentBuilder moduleComponentAssessmentId(String moduleComponentAssessmentId) {
            this.moduleComponentAssessmentId = moduleComponentAssessmentId;
            return this;
        }

        public AssessmentAttachmentBuilder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public AssessmentAttachment build() {
            return new AssessmentAttachment(this.id, this.moduleComponentAssessmentId, this.documentId);
        }

        public String toString() {
            return "AssessmentAttachment.AssessmentAttachmentBuilder(id=" + this.id + ", moduleComponentAssessmentId=" + this.moduleComponentAssessmentId + ", documentId=" + this.documentId + ")";
        }
    }
}
