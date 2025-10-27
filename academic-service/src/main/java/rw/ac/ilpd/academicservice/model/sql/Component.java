package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_components")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String acronym;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_module_id")
    private CurriculumModule curriculumModule;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Integer credits;

    public Component(UUID id, String name, String acronym, CurriculumModule curriculumModule, LocalDateTime createdAt, Integer credits) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.curriculumModule = curriculumModule;
        this.createdAt = createdAt;
        this.credits = credits;
    }

    public Component() {
    }

    public static ComponentBuilder builder() {
        return new ComponentBuilder();
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

    public CurriculumModule getCurriculumModule() {
        return this.curriculumModule;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Integer getCredits() {
        return this.credits;
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

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Component)) return false;
        final Component other = (Component) o;
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
        final Object this$curriculumModule = this.getCurriculumModule();
        final Object other$curriculumModule = other.getCurriculumModule();
        if (this$curriculumModule == null ? other$curriculumModule != null : !this$curriculumModule.equals(other$curriculumModule))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$credits = this.getCredits();
        final Object other$credits = other.getCredits();
        if (this$credits == null ? other$credits != null : !this$credits.equals(other$credits)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Component;
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
        final Object $curriculumModule = this.getCurriculumModule();
        result = result * PRIME + ($curriculumModule == null ? 43 : $curriculumModule.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $credits = this.getCredits();
        result = result * PRIME + ($credits == null ? 43 : $credits.hashCode());
        return result;
    }

    public String toString() {
        return "Component(id=" + this.getId() + ", name=" + this.getName() + ", acronym=" + this.getAcronym() + ", curriculumModule=" + this.getCurriculumModule() + ", createdAt=" + this.getCreatedAt() + ", credits=" + this.getCredits() + ")";
    }

    public static class ComponentBuilder {
        private UUID id;
        private String name;
        private String acronym;
        private CurriculumModule curriculumModule;
        private LocalDateTime createdAt;
        private Integer credits;

        ComponentBuilder() {
        }

        public ComponentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ComponentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ComponentBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public ComponentBuilder curriculumModule(CurriculumModule curriculumModule) {
            this.curriculumModule = curriculumModule;
            return this;
        }

        public ComponentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ComponentBuilder credits(Integer credits) {
            this.credits = credits;
            return this;
        }

        public Component build() {
            return new Component(this.id, this.name, this.acronym, this.curriculumModule, this.createdAt, this.credits);
        }

        public String toString() {
            return "Component.ComponentBuilder(id=" + this.id + ", name=" + this.name + ", acronym=" + this.acronym + ", curriculumModule=" + this.curriculumModule + ", createdAt=" + this.createdAt + ", credits=" + this.credits + ")";
        }
    }
}
