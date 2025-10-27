package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This table tells us the source of items that are in ILPD's stock,
 * whether from ILPD's budget or from external funders.
 */
@Entity
@Table(name = "inv_sources_of_funds")
public class SourceOfFund {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String name;

    private String description;

    private String phone;

    private String email;

    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public SourceOfFund() {
    }

    public SourceOfFund(UUID id, String name, String description, String phone, String email, boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public static SourceOfFundBuilder builder() {
        return new SourceOfFundBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SourceOfFund{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", description: '" + description + '\'' +
                ", phone: '" + phone + '\'' +
                ", email: '" + email + '\'' +
                ", isDeleted: " + isDeleted +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class SourceOfFundBuilder {
        private UUID id;
        private String name;
        private String description;
        private String phone;
        private String email;
        private boolean isDeleted;
        private LocalDateTime createdAt;

        SourceOfFundBuilder() {
        }

        public SourceOfFundBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SourceOfFundBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SourceOfFundBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SourceOfFundBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public SourceOfFundBuilder email(String email) {
            this.email = email;
            return this;
        }

        public SourceOfFundBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public SourceOfFundBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SourceOfFund build() {
            return new SourceOfFund(this.id, this.name, this.description, this.phone, this.email, this.isDeleted, this.createdAt);
        }

        public String toString() {
            return "SourceOfFund.SourceOfFundBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", phone=" + this.phone + ", email=" + this.email + ", isDeleted=" + this.isDeleted + ", createdAt=" + this.createdAt + ")";
        }
    }
}
