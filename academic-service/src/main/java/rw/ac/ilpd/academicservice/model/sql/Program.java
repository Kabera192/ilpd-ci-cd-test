package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String acronym;

    @ManyToOne
    private ProgramType programType;

    private boolean isDeleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Program(UUID id, String code, String name, String description, String acronym, ProgramType programType, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.acronym = acronym;
        this.programType = programType;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Program() {
    }

    public static ProgramBuilder builder() {
        return new ProgramBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public ProgramType getProgramType() {
        return this.programType;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Program)) return false;
        final Program other = (Program) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$acronym = this.getAcronym();
        final Object other$acronym = other.getAcronym();
        if (this$acronym == null ? other$acronym != null : !this$acronym.equals(other$acronym)) return false;
        final Object this$programType = this.getProgramType();
        final Object other$programType = other.getProgramType();
        if (this$programType == null ? other$programType != null : !this$programType.equals(other$programType))
            return false;
        if (this.getIsDeleted() != other.getIsDeleted()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Program;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $acronym = this.getAcronym();
        result = result * PRIME + ($acronym == null ? 43 : $acronym.hashCode());
        final Object $programType = this.getProgramType();
        result = result * PRIME + ($programType == null ? 43 : $programType.hashCode());
        result = result * PRIME + (this.getIsDeleted() ? 79 : 97);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        return "Program(id=" + this.getId() + ", code=" + this.getCode() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", acronym=" + this.getAcronym() + ", programType=" + this.getProgramType() + ", isDeleted=" + this.getIsDeleted() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class ProgramBuilder {
        private UUID id;
        private String code;
        private String name;
        private String description;
        private String acronym;
        private ProgramType programType;
        private boolean isDeleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        ProgramBuilder() {
        }

        public ProgramBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProgramBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ProgramBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProgramBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProgramBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public ProgramBuilder programType(ProgramType programType) {
            this.programType = programType;
            return this;
        }

        public ProgramBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public ProgramBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProgramBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Program build() {
            return new Program(this.id, this.code, this.name, this.description, this.acronym, this.programType, this.isDeleted, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "Program.ProgramBuilder(id=" + this.id + ", code=" + this.code + ", name=" + this.name + ", description=" + this.description + ", acronym=" + this.acronym + ", programType=" + this.programType + ", isDeleted=" + this.isDeleted + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
