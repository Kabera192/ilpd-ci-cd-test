package rw.ac.ilpd.mis.auth.entity.mongo;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */

@Document(collection = "auth_external_users")
public class ExternalUserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String gender;
    private String tinNumber;
    private String nationality;
    private String position;
    private String address;
    private String institution;
    private String phoneNumber;
    private String relationShip;
    private String sponsorType;
    private String type;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    private String documentId;

    public ExternalUserEntity(String id, String name, String email, String gender, String tinNumber, String nationality, String position, String address, String institution, String phoneNumber, String relationShip, String sponsorType, String type, LocalDateTime createdAt, String createdBy, String documentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.tinNumber = tinNumber;
        this.nationality = nationality;
        this.position = position;
        this.address = address;
        this.institution = institution;
        this.phoneNumber = phoneNumber;
        this.relationShip = relationShip;
        this.sponsorType = sponsorType;
        this.type = type;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.documentId = documentId;
    }

    public ExternalUserEntity() {
    }

    public static ExternalUserEntityBuilder builder() {
        return new ExternalUserEntityBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public String getTinNumber() {
        return this.tinNumber;
    }

    public String getNationality() {
        return this.nationality;
    }

    public String getPosition() {
        return this.position;
    }

    public String getAddress() {
        return this.address;
    }

    public String getInstitution() {
        return this.institution;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getRelationShip() {
        return this.relationShip;
    }

    public String getSponsorType() {
        return this.sponsorType;
    }

    public String getType() {
        return this.type;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String toString() {
        return "ExternalUserEntity(id=" + this.getId() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", gender=" + this.getGender() + ", tinNumber=" + this.getTinNumber() + ", nationality=" + this.getNationality() + ", position=" + this.getPosition() + ", address=" + this.getAddress() + ", institution=" + this.getInstitution() + ", phoneNumber=" + this.getPhoneNumber() + ", relationShip=" + this.getRelationShip() + ", sponsorType=" + this.getSponsorType() + ", type=" + this.getType() + ", createdAt=" + this.getCreatedAt() + ", createdBy=" + this.getCreatedBy() + ", documentId=" + this.getDocumentId() + ")";
    }

    public static class ExternalUserEntityBuilder {
        private String id;
        private String name;
        private String email;
        private String gender;
        private String tinNumber;
        private String nationality;
        private String position;
        private String address;
        private String institution;
        private String phoneNumber;
        private String relationShip;
        private String sponsorType;
        private String type;
        private LocalDateTime createdAt;
        private String createdBy;
        private String documentId;

        ExternalUserEntityBuilder() {
        }

        public ExternalUserEntityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ExternalUserEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ExternalUserEntityBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ExternalUserEntityBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public ExternalUserEntityBuilder tinNumber(String tinNumber) {
            this.tinNumber = tinNumber;
            return this;
        }

        public ExternalUserEntityBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public ExternalUserEntityBuilder position(String position) {
            this.position = position;
            return this;
        }

        public ExternalUserEntityBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ExternalUserEntityBuilder institution(String institution) {
            this.institution = institution;
            return this;
        }

        public ExternalUserEntityBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ExternalUserEntityBuilder relationShip(String relationShip) {
            this.relationShip = relationShip;
            return this;
        }

        public ExternalUserEntityBuilder sponsorType(String sponsorType) {
            this.sponsorType = sponsorType;
            return this;
        }

        public ExternalUserEntityBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ExternalUserEntityBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ExternalUserEntityBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ExternalUserEntityBuilder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public ExternalUserEntity build() {
            return new ExternalUserEntity(this.id, this.name, this.email, this.gender, this.tinNumber, this.nationality, this.position, this.address, this.institution, this.phoneNumber, this.relationShip, this.sponsorType, this.type, this.createdAt, this.createdBy, this.documentId);
        }

        public String toString() {
            return "ExternalUserEntity.ExternalUserEntityBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", gender=" + this.gender + ", tinNumber=" + this.tinNumber + ", nationality=" + this.nationality + ", position=" + this.position + ", address=" + this.address + ", institution=" + this.institution + ", phoneNumber=" + this.phoneNumber + ", relationShip=" + this.relationShip + ", sponsorType=" + this.sponsorType + ", type=" + this.type + ", createdAt=" + this.createdAt + ", createdBy=" + this.createdBy + ", documentId=" + this.documentId + ")";
        }
    }
}
