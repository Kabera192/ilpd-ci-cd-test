package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aca_possible_session_couples")
public class PossibleSessionCouple {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyModeSession session1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyModeSession session2Id;

    private Boolean deletedStatus;

    public PossibleSessionCouple(UUID id, StudyModeSession session1Id, StudyModeSession session2Id, Boolean deletedStatus) {
        this.id = id;
        this.session1Id = session1Id;
        this.session2Id = session2Id;
        this.deletedStatus = deletedStatus;
    }

    public PossibleSessionCouple() {
    }

    public static PossibleSessionCoupleBuilder builder() {
        return new PossibleSessionCoupleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public StudyModeSession getSession1Id() {
        return this.session1Id;
    }

    public StudyModeSession getSession2Id() {
        return this.session2Id;
    }

    public Boolean getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSession1Id(StudyModeSession session1Id) {
        this.session1Id = session1Id;
    }

    public void setSession2Id(StudyModeSession session2Id) {
        this.session2Id = session2Id;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PossibleSessionCouple)) return false;
        final PossibleSessionCouple other = (PossibleSessionCouple) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$session1Id = this.getSession1Id();
        final Object other$session1Id = other.getSession1Id();
        if (this$session1Id == null ? other$session1Id != null : !this$session1Id.equals(other$session1Id))
            return false;
        final Object this$session2Id = this.getSession2Id();
        final Object other$session2Id = other.getSession2Id();
        if (this$session2Id == null ? other$session2Id != null : !this$session2Id.equals(other$session2Id))
            return false;
        final Object this$deletedStatus = this.getDeletedStatus();
        final Object other$deletedStatus = other.getDeletedStatus();
        if (this$deletedStatus == null ? other$deletedStatus != null : !this$deletedStatus.equals(other$deletedStatus))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PossibleSessionCouple;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $session1Id = this.getSession1Id();
        result = result * PRIME + ($session1Id == null ? 43 : $session1Id.hashCode());
        final Object $session2Id = this.getSession2Id();
        result = result * PRIME + ($session2Id == null ? 43 : $session2Id.hashCode());
        final Object $deletedStatus = this.getDeletedStatus();
        result = result * PRIME + ($deletedStatus == null ? 43 : $deletedStatus.hashCode());
        return result;
    }

    public String toString() {
        return "PossibleSessionCouple(id=" + this.getId() + ", session1Id=" + this.getSession1Id() + ", session2Id=" + this.getSession2Id() + ", deletedStatus=" + this.getDeletedStatus() + ")";
    }

    public static class PossibleSessionCoupleBuilder {
        private UUID id;
        private StudyModeSession session1Id;
        private StudyModeSession session2Id;
        private Boolean deletedStatus;

        PossibleSessionCoupleBuilder() {
        }

        public PossibleSessionCoupleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PossibleSessionCoupleBuilder session1Id(StudyModeSession session1Id) {
            this.session1Id = session1Id;
            return this;
        }

        public PossibleSessionCoupleBuilder session2Id(StudyModeSession session2Id) {
            this.session2Id = session2Id;
            return this;
        }

        public PossibleSessionCoupleBuilder deletedStatus(Boolean deletedStatus) {
            this.deletedStatus = deletedStatus;
            return this;
        }

        public PossibleSessionCouple build() {
            return new PossibleSessionCouple(this.id, this.session1Id, this.session2Id, this.deletedStatus);
        }

        public String toString() {
            return "PossibleSessionCouple.PossibleSessionCoupleBuilder(id=" + this.id + ", session1Id=" + this.session1Id + ", session2Id=" + this.session2Id + ", deletedStatus=" + this.deletedStatus + ")";
        }
    }
}
