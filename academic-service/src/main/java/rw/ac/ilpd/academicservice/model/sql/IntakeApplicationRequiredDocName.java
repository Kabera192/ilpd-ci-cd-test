package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aca_intake_application_required_doc_names")
public class IntakeApplicationRequiredDocName {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    public IntakeApplicationRequiredDocName(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public IntakeApplicationRequiredDocName() {
    }

    public static IntakeApplicationRequiredDocNameBuilder builder() {
        return new IntakeApplicationRequiredDocNameBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeApplicationRequiredDocName)) return false;
        final IntakeApplicationRequiredDocName other = (IntakeApplicationRequiredDocName) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeApplicationRequiredDocName;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeApplicationRequiredDocName(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public static class IntakeApplicationRequiredDocNameBuilder {
        private UUID id;
        private String name;

        IntakeApplicationRequiredDocNameBuilder() {
        }

        public IntakeApplicationRequiredDocNameBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IntakeApplicationRequiredDocNameBuilder name(String name) {
            this.name = name;
            return this;
        }

        public IntakeApplicationRequiredDocName build() {
            return new IntakeApplicationRequiredDocName(this.id, this.name);
        }

        public String toString() {
            return "IntakeApplicationRequiredDocName.IntakeApplicationRequiredDocNameBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
