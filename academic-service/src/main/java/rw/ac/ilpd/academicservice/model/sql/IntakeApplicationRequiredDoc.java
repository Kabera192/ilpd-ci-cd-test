package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aca_intake_application_required_docs")
public class IntakeApplicationRequiredDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;

    @ManyToOne(fetch = FetchType.LAZY)
    private IntakeApplicationRequiredDocName documentRequiredName;

    private Boolean isRequired;

    public IntakeApplicationRequiredDoc(UUID id, Intake intake, IntakeApplicationRequiredDocName documentRequiredName, Boolean isRequired) {
        this.id = id;
        this.intake = intake;
        this.documentRequiredName = documentRequiredName;
        this.isRequired = isRequired;
    }

    public IntakeApplicationRequiredDoc() {
    }

    public static IntakeApplicationRequiredDocBuilder builder() {
        return new IntakeApplicationRequiredDocBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Intake getIntake() {
        return this.intake;
    }

    public IntakeApplicationRequiredDocName getDocumentRequiredName() {
        return this.documentRequiredName;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setDocumentRequiredName(IntakeApplicationRequiredDocName documentRequiredName) {
        this.documentRequiredName = documentRequiredName;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeApplicationRequiredDoc)) return false;
        final IntakeApplicationRequiredDoc other = (IntakeApplicationRequiredDoc) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$intake = this.getIntake();
        final Object other$intake = other.getIntake();
        if (this$intake == null ? other$intake != null : !this$intake.equals(other$intake)) return false;
        final Object this$documentRequiredName = this.getDocumentRequiredName();
        final Object other$documentRequiredName = other.getDocumentRequiredName();
        if (this$documentRequiredName == null ? other$documentRequiredName != null : !this$documentRequiredName.equals(other$documentRequiredName))
            return false;
        final Object this$isRequired = this.getIsRequired();
        final Object other$isRequired = other.getIsRequired();
        if (this$isRequired == null ? other$isRequired != null : !this$isRequired.equals(other$isRequired))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeApplicationRequiredDoc;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $intake = this.getIntake();
        result = result * PRIME + ($intake == null ? 43 : $intake.hashCode());
        final Object $documentRequiredName = this.getDocumentRequiredName();
        result = result * PRIME + ($documentRequiredName == null ? 43 : $documentRequiredName.hashCode());
        final Object $isRequired = this.getIsRequired();
        result = result * PRIME + ($isRequired == null ? 43 : $isRequired.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeApplicationRequiredDoc(id=" + this.getId() + ", intake=" + this.getIntake() + ", documentRequiredName=" + this.getDocumentRequiredName() + ", isRequired=" + this.getIsRequired() + ")";
    }

    public static class IntakeApplicationRequiredDocBuilder {
        private UUID id;
        private Intake intake;
        private IntakeApplicationRequiredDocName documentRequiredName;
        private Boolean isRequired;

        IntakeApplicationRequiredDocBuilder() {
        }

        public IntakeApplicationRequiredDocBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IntakeApplicationRequiredDocBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public IntakeApplicationRequiredDocBuilder documentRequiredName(IntakeApplicationRequiredDocName documentRequiredName) {
            this.documentRequiredName = documentRequiredName;
            return this;
        }

        public IntakeApplicationRequiredDocBuilder isRequired(Boolean isRequired) {
            this.isRequired = isRequired;
            return this;
        }

        public IntakeApplicationRequiredDoc build() {
            return new IntakeApplicationRequiredDoc(this.id, this.intake, this.documentRequiredName, this.isRequired);
        }

        public String toString() {
            return "IntakeApplicationRequiredDoc.IntakeApplicationRequiredDocBuilder(id=" + this.id + ", intake=" + this.intake + ", documentRequiredName=" + this.documentRequiredName + ", isRequired=" + this.isRequired + ")";
        }
    }
}
