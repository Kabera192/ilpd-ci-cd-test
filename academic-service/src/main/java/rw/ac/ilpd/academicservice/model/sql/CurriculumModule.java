package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "aca_curriculum_modules")
@Builder
public class CurriculumModule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    private Integer moduleOrder;
    private Integer credits;

    public CurriculumModule(UUID id, Curriculum curriculum, Module module, Integer moduleOrder, Integer credits) {
        this.id = id;
        this.curriculum = curriculum;
        this.module = module;
        this.moduleOrder = moduleOrder;
        this.credits = credits;
    }

    public CurriculumModule() {
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public static CurriculumModuleBuilder builder() {
        return new CurriculumModuleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Curriculum getCurriculum() {
        return this.curriculum;
    }

    public Module getModule() {
        return this.module;
    }

    public Integer getCredits() {
        return this.credits;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CurriculumModule)) return false;
        final CurriculumModule other = (CurriculumModule) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$curriculum = this.getCurriculum();
        final Object other$curriculum = other.getCurriculum();
        if (this$curriculum == null ? other$curriculum != null : !this$curriculum.equals(other$curriculum))
            return false;
        final Object this$module = this.getModule();
        final Object other$module = other.getModule();
        if (this$module == null ? other$module != null : !this$module.equals(other$module)) return false;
        final Object this$credits = this.getCredits();
        final Object other$credits = other.getCredits();
        if (this$credits == null ? other$credits != null : !this$credits.equals(other$credits)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CurriculumModule;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $curriculum = this.getCurriculum();
        result = result * PRIME + ($curriculum == null ? 43 : $curriculum.hashCode());
        final Object $module = this.getModule();
        result = result * PRIME + ($module == null ? 43 : $module.hashCode());
        final Object $credits = this.getCredits();
        result = result * PRIME + ($credits == null ? 43 : $credits.hashCode());
        return result;
    }

    public String toString() {
        return "CurriculumModule(id=" + this.getId() + ", curriculum=" + this.getCurriculum() + ", module=" + this.getModule() + ", credits=" + this.getCredits() + ")";
    }


}
