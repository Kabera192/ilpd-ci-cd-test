package rw.ac.ilpd.academicservice.model.sql;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import rw.ac.ilpd.academicservice.config.TrimmingDeserializer;
import rw.ac.ilpd.sharedlibrary.enums.ModuleType;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "aca_modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    @JsonDeserialize(using = TrimmingDeserializer.class)
    private String name;
    @JsonDeserialize(using = TrimmingDeserializer.class)
    private String code;

    @Enumerated(EnumType.STRING)
    private ModuleType type;

    private Boolean deleteStatus = false;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "module",targetEntity = HeadOfModule.class)
    private List<HeadOfModule> headOfModules;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "module",targetEntity = HeadOfModule.class)
    private List<CurriculumModule>  curriculumModules;

    public List<CurriculumModule> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(List<CurriculumModule> curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public List<HeadOfModule> getHeadOfModules() {
        return headOfModules;
    }

    public void setHeadOfModules(List<HeadOfModule> headOfModules) {
        this.headOfModules = headOfModules;
    }

    public Module(UUID id, String name, String code, ModuleType type, Boolean deleteStatus) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.deleteStatus = deleteStatus;
    }

    public Module() {
    }

    public static ModuleBuilder builder() {
        return new ModuleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }


    public ModuleType getType() {
        return this.type;
    }

    public Boolean getDeleteStatus() {
        return this.deleteStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setType(ModuleType type) {
        this.type = type;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Module)) return false;
        final Module other = (Module) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$deleteStatus = this.getDeleteStatus();
        final Object other$deleteStatus = other.getDeleteStatus();
        if (this$deleteStatus == null ? other$deleteStatus != null : !this$deleteStatus.equals(other$deleteStatus))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Module;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $deleteStatus = this.getDeleteStatus();
        result = result * PRIME + ($deleteStatus == null ? 43 : $deleteStatus.hashCode());
        return result;
    }

    public String toString() {
        return "Module(id=" + this.getId() + ", name=" + this.getName() + ", code=" + this.getCode() + ", type=" + this.getType() + ", deleteStatus=" + this.getDeleteStatus() + ")";
    }

    public static class ModuleBuilder {
        private UUID id;
        private String name;
        private String code;
        private ModuleType type;
        private Boolean deleteStatus;

        ModuleBuilder() {
        }

        public ModuleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ModuleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ModuleBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ModuleBuilder type(ModuleType type) {
            this.type = type;
            return this;
        }

        public ModuleBuilder deleteStatus(Boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public Module build() {
            return new Module(this.id, this.name, this.code, this.type, this.deleteStatus);
        }

        public String toString() {
            return "Module.ModuleBuilder(id=" + this.id + ", name=" + this.name + ", code=" + this.code + ", type=" + this.type + ", deleteStatus=" + this.deleteStatus + ")";
        }
    }
}
