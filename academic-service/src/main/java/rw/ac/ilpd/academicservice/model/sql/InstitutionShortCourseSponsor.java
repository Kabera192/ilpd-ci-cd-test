package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import rw.ac.ilpd.sharedlibrary.enums.SponsorType;

import java.util.UUID;

@Entity
@Table(name = "aca_institution_short_course_sponsors")
public class InstitutionShortCourseSponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private SponsorType type;

    public InstitutionShortCourseSponsor(UUID id, String name, String email, String phone, String address, SponsorType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
    }

    public InstitutionShortCourseSponsor() {
    }

    public static InstitutionShortCourseSponsorBuilder builder() {
        return new InstitutionShortCourseSponsorBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }

    public SponsorType getType() {
        return this.type;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(SponsorType type) {
        this.type = type;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InstitutionShortCourseSponsor)) return false;
        final InstitutionShortCourseSponsor other = (InstitutionShortCourseSponsor) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$phone = this.getPhone();
        final Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InstitutionShortCourseSponsor;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "InstitutionShortCourseSponsor(id=" + this.getId() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", address=" + this.getAddress() + ", type=" + this.getType() + ")";
    }

    public static class InstitutionShortCourseSponsorBuilder {
        private UUID id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private SponsorType type;

        InstitutionShortCourseSponsorBuilder() {
        }

        public InstitutionShortCourseSponsorBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public InstitutionShortCourseSponsorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InstitutionShortCourseSponsorBuilder email(String email) {
            this.email = email;
            return this;
        }

        public InstitutionShortCourseSponsorBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public InstitutionShortCourseSponsorBuilder address(String address) {
            this.address = address;
            return this;
        }

        public InstitutionShortCourseSponsorBuilder type(SponsorType type) {
            this.type = type;
            return this;
        }

        public InstitutionShortCourseSponsor build() {
            return new InstitutionShortCourseSponsor(this.id, this.name, this.email, this.phone, this.address, this.type);
        }

        public String toString() {
            return "InstitutionShortCourseSponsor.InstitutionShortCourseSponsorBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", phone=" + this.phone + ", address=" + this.address + ", type=" + this.type + ")";
        }
    }
}
