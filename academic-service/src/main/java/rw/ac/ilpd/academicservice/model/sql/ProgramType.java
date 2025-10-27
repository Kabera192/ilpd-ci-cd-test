package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_program_types")
public class ProgramType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(name = "delete_status")
    private boolean deleteStatus = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ProgramType(UUID id, String name, boolean deleteStatus, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.createdAt = createdAt;
    }

    public ProgramType() {
    }

    public static ProgramTypeBuilder builder() {
        return new ProgramTypeBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
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

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProgramType)) return false;
        final ProgramType other = (ProgramType) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.isDeleteStatus() != other.isDeleteStatus()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProgramType;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + (this.isDeleteStatus() ? 79 : 97);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ProgramType(id=" + this.getId() + ", name=" + this.getName() + ", deleteStatus=" + this.isDeleteStatus() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ProgramTypeBuilder {
        private UUID id;
        private String name;
        private boolean deleteStatus;
        private LocalDateTime createdAt;

        ProgramTypeBuilder() {
        }

        public ProgramTypeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProgramTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProgramTypeBuilder deleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public ProgramTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProgramType build() {
            return new ProgramType(this.id, this.name, this.deleteStatus, this.createdAt);
        }

        public String toString() {
            return "ProgramType.ProgramTypeBuilder(id=" + this.id + ", name=" + this.name + ", deleteStatus=" + this.deleteStatus + ", createdAt=" + this.createdAt + ")";
        }
    }
}
