package rw.ac.ilpd.reportingservice.model.nosql.embedding;

import rw.ac.ilpd.sharedlibrary.enums.EvaluationFormUserFillerLevel;

import java.util.UUID;

public class EvaluationFormUserFiller {
    private String id = UUID.randomUUID().toString();
    private EvaluationFormUserFillerLevel level;
    private UUID levelId;
    private UUID roleId;
    private UUID intakeId;

    public EvaluationFormUserFiller(String id, EvaluationFormUserFillerLevel level, UUID levelId, UUID roleId, UUID intakeId) {
        this.id = id;
        this.level = level;
        this.levelId = levelId;
        this.roleId = roleId;
        this.intakeId = intakeId;
    }

    public EvaluationFormUserFiller() {
    }

    public static EvaluationFormUserFillerBuilder builder() {
        return new EvaluationFormUserFillerBuilder();
    }

    public String getId() {
        return this.id;
    }

    public EvaluationFormUserFillerLevel getLevel() {
        return this.level;
    }

    public UUID getLevelId() {
        return this.levelId;
    }

    public UUID getRoleId() {
        return this.roleId;
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(EvaluationFormUserFillerLevel level) {
        this.level = level;
    }

    public void setLevelId(UUID levelId) {
        this.levelId = levelId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EvaluationFormUserFiller)) return false;
        final EvaluationFormUserFiller other = (EvaluationFormUserFiller) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$level = this.getLevel();
        final Object other$level = other.getLevel();
        if (this$level == null ? other$level != null : !this$level.equals(other$level)) return false;
        final Object this$levelId = this.getLevelId();
        final Object other$levelId = other.getLevelId();
        if (this$levelId == null ? other$levelId != null : !this$levelId.equals(other$levelId)) return false;
        final Object this$roleId = this.getRoleId();
        final Object other$roleId = other.getRoleId();
        if (this$roleId == null ? other$roleId != null : !this$roleId.equals(other$roleId)) return false;
        final Object this$intakeId = this.getIntakeId();
        final Object other$intakeId = other.getIntakeId();
        if (this$intakeId == null ? other$intakeId != null : !this$intakeId.equals(other$intakeId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EvaluationFormUserFiller;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $level = this.getLevel();
        result = result * PRIME + ($level == null ? 43 : $level.hashCode());
        final Object $levelId = this.getLevelId();
        result = result * PRIME + ($levelId == null ? 43 : $levelId.hashCode());
        final Object $roleId = this.getRoleId();
        result = result * PRIME + ($roleId == null ? 43 : $roleId.hashCode());
        final Object $intakeId = this.getIntakeId();
        result = result * PRIME + ($intakeId == null ? 43 : $intakeId.hashCode());
        return result;
    }

    public String toString() {
        return "EvaluationFormUserFiller(id=" + this.getId() + ", level=" + this.getLevel() + ", levelId=" + this.getLevelId() + ", roleId=" + this.getRoleId() + ", intakeId=" + this.getIntakeId() + ")";
    }

    public static class EvaluationFormUserFillerBuilder {
        private String id;
        private EvaluationFormUserFillerLevel level;
        private UUID levelId;
        private UUID roleId;
        private UUID intakeId;

        EvaluationFormUserFillerBuilder() {
        }

        public EvaluationFormUserFillerBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EvaluationFormUserFillerBuilder level(EvaluationFormUserFillerLevel level) {
            this.level = level;
            return this;
        }

        public EvaluationFormUserFillerBuilder levelId(UUID levelId) {
            this.levelId = levelId;
            return this;
        }

        public EvaluationFormUserFillerBuilder roleId(UUID roleId) {
            this.roleId = roleId;
            return this;
        }

        public EvaluationFormUserFillerBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public EvaluationFormUserFiller build() {
            return new EvaluationFormUserFiller(this.id, this.level, this.levelId, this.roleId, this.intakeId);
        }

        public String toString() {
            return "EvaluationFormUserFiller.EvaluationFormUserFillerBuilder(id=" + this.id + ", level=" + this.level + ", levelId=" + this.levelId + ", roleId=" + this.roleId + ", intakeId=" + this.intakeId + ")";
        }
    }
}
