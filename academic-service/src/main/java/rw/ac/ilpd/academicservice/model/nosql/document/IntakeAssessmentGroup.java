package rw.ac.ilpd.academicservice.model.nosql.document;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.academicservice.model.nosql.embedding.IntakeAssessmentGroupStudent;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * This entity defines an assessment group for a particular component in cases
 * where the lecturer wishes to set group assessments. The design should allow the
 * lecturer to create new groups for each group assessment or reuse the existing groups.
 */

@Document(collection = "aca_intake_assessment_groups")
public class IntakeAssessmentGroup {
    @Id
    private String id;

    private UUID componentId;

    private UUID intakeId;

    private String name;

    @CreatedDate
    private LocalDate createdAt;

    List<IntakeAssessmentGroupStudent> students;

    public IntakeAssessmentGroup(String id, UUID componentId, UUID intakeId, String name, LocalDate createdAt, List<IntakeAssessmentGroupStudent> students) {
        this.id = id;
        this.componentId = componentId;
        this.intakeId = intakeId;
        this.name = name;
        this.createdAt = createdAt;
        this.students = students;
    }

    public IntakeAssessmentGroup() {
    }

    public static IntakeAssessmentGroupBuilder builder() {
        return new IntakeAssessmentGroupBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getComponentId() {
        return this.componentId;
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public List<IntakeAssessmentGroupStudent> getStudents() {
        return this.students;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setStudents(List<IntakeAssessmentGroupStudent> students) {
        this.students = students;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeAssessmentGroup)) return false;
        final IntakeAssessmentGroup other = (IntakeAssessmentGroup) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$componentId = this.getComponentId();
        final Object other$componentId = other.getComponentId();
        if (this$componentId == null ? other$componentId != null : !this$componentId.equals(other$componentId))
            return false;
        final Object this$intakeId = this.getIntakeId();
        final Object other$intakeId = other.getIntakeId();
        if (this$intakeId == null ? other$intakeId != null : !this$intakeId.equals(other$intakeId)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$students = this.getStudents();
        final Object other$students = other.getStudents();
        if (this$students == null ? other$students != null : !this$students.equals(other$students)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeAssessmentGroup;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $componentId = this.getComponentId();
        result = result * PRIME + ($componentId == null ? 43 : $componentId.hashCode());
        final Object $intakeId = this.getIntakeId();
        result = result * PRIME + ($intakeId == null ? 43 : $intakeId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $students = this.getStudents();
        result = result * PRIME + ($students == null ? 43 : $students.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeAssessmentGroup(id=" + this.getId() + ", componentId=" + this.getComponentId() + ", intakeId=" + this.getIntakeId() + ", name=" + this.getName() + ", createdAt=" + this.getCreatedAt() + ", students=" + this.getStudents() + ")";
    }

    public static class IntakeAssessmentGroupBuilder {
        private String id;
        private UUID componentId;
        private UUID intakeId;
        private String name;
        private LocalDate createdAt;
        private List<IntakeAssessmentGroupStudent> students;

        IntakeAssessmentGroupBuilder() {
        }

        public IntakeAssessmentGroupBuilder id(String id) {
            this.id = id;
            return this;
        }

        public IntakeAssessmentGroupBuilder componentId(UUID componentId) {
            this.componentId = componentId;
            return this;
        }

        public IntakeAssessmentGroupBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public IntakeAssessmentGroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public IntakeAssessmentGroupBuilder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IntakeAssessmentGroupBuilder students(List<IntakeAssessmentGroupStudent> students) {
            this.students = students;
            return this;
        }

        public IntakeAssessmentGroup build() {
            return new IntakeAssessmentGroup(this.id, this.componentId, this.intakeId, this.name, this.createdAt, this.students);
        }

        public String toString() {
            return "IntakeAssessmentGroup.IntakeAssessmentGroupBuilder(id=" + this.id + ", componentId=" + this.componentId + ", intakeId=" + this.intakeId + ", name=" + this.name + ", createdAt=" + this.createdAt + ", students=" + this.students + ")";
        }
    }
}
