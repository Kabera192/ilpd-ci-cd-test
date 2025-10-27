package rw.ac.ilpd.academicservice.model.nosql.embedding;


import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity creates a link between a student and an assessment
 * group they have been assigned to. It is worth noting that a student
 * could belong to multiple groups and a group usually has multiple students.
 */

public class IntakeAssessmentGroupStudent {
    private String id = UUID.randomUUID().toString();

    // private String groupId; // No longer necessary since it is embedded in the IntakeAssessmentGroup entity.

    private UUID studentId;

    private boolean isLeader;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime joinedAt;

    private LocalDateTime leftAt;


    public IntakeAssessmentGroupStudent(String id, UUID studentId, boolean isLeader, LocalDateTime createdAt, LocalDateTime joinedAt, LocalDateTime leftAt) {
        this.id = id;
        this.studentId = studentId;
        this.isLeader = isLeader;
        this.createdAt = createdAt;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
    }

    public IntakeAssessmentGroupStudent() {
    }


    public static IntakeAssessmentGroupStudentBuilder builder() {
        return new IntakeAssessmentGroupStudentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getStudentId() {
        return this.studentId;
    }

    public boolean isLeader() {
        return this.isLeader;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getJoinedAt() {
        return this.joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return this.leftAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public void setLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeAssessmentGroupStudent)) return false;
        final IntakeAssessmentGroupStudent other = (IntakeAssessmentGroupStudent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$studentId = this.getStudentId();
        final Object other$studentId = other.getStudentId();
        if (this$studentId == null ? other$studentId != null : !this$studentId.equals(other$studentId)) return false;
        if (this.isLeader() != other.isLeader()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$joinedAt = this.getJoinedAt();
        final Object other$joinedAt = other.getJoinedAt();
        if (this$joinedAt == null ? other$joinedAt != null : !this$joinedAt.equals(other$joinedAt)) return false;
        final Object this$leftAt = this.getLeftAt();
        final Object other$leftAt = other.getLeftAt();
        if (this$leftAt == null ? other$leftAt != null : !this$leftAt.equals(other$leftAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeAssessmentGroupStudent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $studentId = this.getStudentId();
        result = result * PRIME + ($studentId == null ? 43 : $studentId.hashCode());
        result = result * PRIME + (this.isLeader() ? 79 : 97);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $joinedAt = this.getJoinedAt();
        result = result * PRIME + ($joinedAt == null ? 43 : $joinedAt.hashCode());
        final Object $leftAt = this.getLeftAt();
        result = result * PRIME + ($leftAt == null ? 43 : $leftAt.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeAssessmentGroupStudent(id=" + this.getId() + ", studentId=" + this.getStudentId() + ", isLeader=" + this.isLeader() + ", createdAt=" + this.getCreatedAt() + ", joinedAt=" + this.getJoinedAt() + ", leftAt=" + this.getLeftAt() + ")";
    }

    public static class IntakeAssessmentGroupStudentBuilder {
        private String id;
        private UUID studentId;
        private boolean isLeader;
        private LocalDateTime createdAt;
        private LocalDateTime joinedAt;
        private LocalDateTime leftAt;

        IntakeAssessmentGroupStudentBuilder() {
        }

        public IntakeAssessmentGroupStudentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public IntakeAssessmentGroupStudentBuilder studentId(UUID studentId) {
            this.studentId = studentId;
            return this;
        }

        public IntakeAssessmentGroupStudentBuilder isLeader(boolean isLeader) {
            this.isLeader = isLeader;
            return this;
        }

        public IntakeAssessmentGroupStudentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IntakeAssessmentGroupStudentBuilder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public IntakeAssessmentGroupStudentBuilder leftAt(LocalDateTime leftAt) {
            this.leftAt = leftAt;
            return this;
        }

        public IntakeAssessmentGroupStudent build() {
            return new IntakeAssessmentGroupStudent(this.id, this.studentId, this.isLeader, this.createdAt, this.joinedAt, this.leftAt);
        }

        public String toString() {
            return "IntakeAssessmentGroupStudent.IntakeAssessmentGroupStudentBuilder(id=" + this.id + ", studentId=" + this.studentId + ", isLeader=" + this.isLeader + ", createdAt=" + this.createdAt + ", joinedAt=" + this.joinedAt + ", leftAt=" + this.leftAt + ")";
        }
    }
}
