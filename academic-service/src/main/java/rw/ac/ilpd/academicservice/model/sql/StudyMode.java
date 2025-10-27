package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_study_modes")
public class StudyMode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String acronym;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public StudyMode(UUID id, String name, String acronym, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.createdAt = createdAt;
    }

    public StudyMode() {
    }

    public static StudyModeBuilder builder() {
        return new StudyModeBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAcronym() {
        return this.acronym;
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

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof StudyMode)) return false;
        final StudyMode other = (StudyMode) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$acronym = this.getAcronym();
        final Object other$acronym = other.getAcronym();
        if (this$acronym == null ? other$acronym != null : !this$acronym.equals(other$acronym)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StudyMode;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $acronym = this.getAcronym();
        result = result * PRIME + ($acronym == null ? 43 : $acronym.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "StudyMode(id=" + this.getId() + ", name=" + this.getName() + ", acronym=" + this.getAcronym() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class StudyModeBuilder {
        private UUID id;
        private String name;
        private String acronym;
        private LocalDateTime createdAt;

        StudyModeBuilder() {
        }

        public StudyModeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StudyModeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudyModeBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public StudyModeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudyMode build() {
            return new StudyMode(this.id, this.name, this.acronym, this.createdAt);
        }

        public String toString() {
            return "StudyMode.StudyModeBuilder(id=" + this.id + ", name=" + this.name + ", acronym=" + this.acronym + ", createdAt=" + this.createdAt + ")";
        }
    }
}
