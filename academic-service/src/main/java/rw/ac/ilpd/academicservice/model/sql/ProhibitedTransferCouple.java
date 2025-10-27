package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_prohibited_transfer_couples")
@EntityListeners(AuditingEntityListener.class)
public class ProhibitedTransferCouple {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyModeSession fromStudyModeSession;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyModeSession toStudyModeSession;

    private Boolean deletedStatus;
    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public ProhibitedTransferCouple(UUID id, StudyModeSession fromStudyModeSession, StudyModeSession toStudyModeSession, Boolean deletedStatus, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.fromStudyModeSession = fromStudyModeSession;
        this.toStudyModeSession = toStudyModeSession;
        this.deletedStatus = deletedStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public ProhibitedTransferCouple() {
    }

    public static ProhibitedTransferCoupleBuilder builder() {
        return new ProhibitedTransferCoupleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public StudyModeSession getFromStudyModeSession() {
        return this.fromStudyModeSession;
    }

    public StudyModeSession getToStudyModeSession() {
        return this.toStudyModeSession;
    }

    public Boolean getDeletedStatus() {
        return this.deletedStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFromStudyModeSession(StudyModeSession fromStudyModeSession) {
        this.fromStudyModeSession = fromStudyModeSession;
    }

    public void setToStudyModeSession(StudyModeSession toStudyModeSession) {
        this.toStudyModeSession = toStudyModeSession;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String toString() {
        return "ProhibitedTransferCouple(id=" + this.getId() + ", fromStudyModeSession=" + this.getFromStudyModeSession() + ", toStudyModeSession=" + this.getToStudyModeSession() + ", deletedStatus=" + this.getDeletedStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", deletedAt=" + this.getDeletedAt() + ")";
    }

    public static class ProhibitedTransferCoupleBuilder {
        private UUID id;
        private StudyModeSession fromStudyModeSession;
        private StudyModeSession toStudyModeSession;
        private Boolean deletedStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;

        ProhibitedTransferCoupleBuilder() {
        }

        public ProhibitedTransferCoupleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProhibitedTransferCoupleBuilder fromStudyModeSession(StudyModeSession fromStudyModeSession) {
            this.fromStudyModeSession = fromStudyModeSession;
            return this;
        }

        public ProhibitedTransferCoupleBuilder toStudyModeSession(StudyModeSession toStudyModeSession) {
            this.toStudyModeSession = toStudyModeSession;
            return this;
        }

        public ProhibitedTransferCoupleBuilder deletedStatus(Boolean deletedStatus) {
            this.deletedStatus = deletedStatus;
            return this;
        }

        public ProhibitedTransferCoupleBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProhibitedTransferCoupleBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ProhibitedTransferCoupleBuilder deletedAt(LocalDateTime deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public ProhibitedTransferCouple build() {
            return new ProhibitedTransferCouple(this.id, this.fromStudyModeSession, this.toStudyModeSession, this.deletedStatus, this.createdAt, this.updatedAt, this.deletedAt);
        }

        public String toString() {
            return "ProhibitedTransferCouple.ProhibitedTransferCoupleBuilder(id=" + this.id + ", fromStudyModeSession=" + this.fromStudyModeSession + ", toStudyModeSession=" + this.toStudyModeSession + ", deletedStatus=" + this.deletedStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", deletedAt=" + this.deletedAt + ")";
        }
    }
}
