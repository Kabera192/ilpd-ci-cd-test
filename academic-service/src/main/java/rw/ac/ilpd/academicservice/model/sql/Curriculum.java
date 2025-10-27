package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import rw.ac.ilpd.sharedlibrary.enums.CurriculumStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_curriculums")
@EntityListeners(AuditingEntityListener.class)
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private CurriculumStatus status;

    private LocalDateTime endedAt;

    @ManyToOne
    private Program program;
    @CreatedBy
    private UUID createdById;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Curriculum(UUID id, String name, String description, CurriculumStatus status, LocalDateTime endedAt, Program program, UUID createdById, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.endedAt = endedAt;
        this.program = program;
        this.createdById = createdById;
        this.createdAt = createdAt;
    }

    public Curriculum() {
    }

    public static CurriculumBuilder builder() {
        return new CurriculumBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public CurriculumStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getEndedAt() {
        return this.endedAt;
    }

    public Program getProgram() {
        return this.program;
    }

    public UUID getCreatedById() {
        return this.createdById;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(CurriculumStatus status) {
        this.status = status;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Curriculum)) return false;
        final Curriculum other = (Curriculum) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$endedAt = this.getEndedAt();
        final Object other$endedAt = other.getEndedAt();
        if (this$endedAt == null ? other$endedAt != null : !this$endedAt.equals(other$endedAt)) return false;
        final Object this$program = this.getProgram();
        final Object other$program = other.getProgram();
        if (this$program == null ? other$program != null : !this$program.equals(other$program)) return false;
        final Object this$createdById = this.getCreatedById();
        final Object other$createdById = other.getCreatedById();
        if (this$createdById == null ? other$createdById != null : !this$createdById.equals(other$createdById))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Curriculum;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $endedAt = this.getEndedAt();
        result = result * PRIME + ($endedAt == null ? 43 : $endedAt.hashCode());
        final Object $program = this.getProgram();
        result = result * PRIME + ($program == null ? 43 : $program.hashCode());
        final Object $createdById = this.getCreatedById();
        result = result * PRIME + ($createdById == null ? 43 : $createdById.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "Curriculum(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", status=" + this.getStatus() + ", endedAt=" + this.getEndedAt() + ", program=" + this.getProgram() + ", createdById=" + this.getCreatedById() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class CurriculumBuilder {
        private UUID id;
        private String name;
        private String description;
        private CurriculumStatus status;
        private LocalDateTime endedAt;
        private Program program;
        private UUID createdById;
        private LocalDateTime createdAt;

        CurriculumBuilder() {
        }

        public CurriculumBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public CurriculumBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CurriculumBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CurriculumBuilder status(CurriculumStatus status) {
            this.status = status;
            return this;
        }

        public CurriculumBuilder endedAt(LocalDateTime endedAt) {
            this.endedAt = endedAt;
            return this;
        }

        public CurriculumBuilder program(Program program) {
            this.program = program;
            return this;
        }

        public CurriculumBuilder createdById(UUID createdById) {
            this.createdById = createdById;
            return this;
        }

        public CurriculumBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Curriculum build() {
            return new Curriculum(this.id, this.name, this.description, this.status, this.endedAt, this.program, this.createdById, this.createdAt);
        }

        public String toString() {
            return "Curriculum.CurriculumBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", endedAt=" + this.endedAt + ", program=" + this.program + ", createdById=" + this.createdById + ", createdAt=" + this.createdAt + ")";
        }
    }
}
