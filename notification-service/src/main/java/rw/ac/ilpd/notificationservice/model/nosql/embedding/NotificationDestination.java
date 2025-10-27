package rw.ac.ilpd.notificationservice.model.nosql.embedding;

import java.util.UUID;

public class NotificationDestination {
    // Nullable destination references
    private UUID intakeId;       // REF Intake (nullable)
    private UUID roleId;         // REF Role (nullable)
    private UUID moduleId;       // REF Module (nullable)
    private UUID componentId;    // REF Component (nullable)
    private UUID userId;         // REF User (nullable)

    public NotificationDestination(UUID intakeId, UUID roleId, UUID moduleId, UUID componentId, UUID userId) {
        this.intakeId = intakeId;
        this.roleId = roleId;
        this.moduleId = moduleId;
        this.componentId = componentId;
        this.userId = userId;
    }

    public NotificationDestination() {
    }

    public static NotificationDestinationBuilder builder() {
        return new NotificationDestinationBuilder();
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public UUID getRoleId() {
        return this.roleId;
    }

    public UUID getModuleId() {
        return this.moduleId;
    }

    public UUID getComponentId() {
        return this.componentId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public static class NotificationDestinationBuilder {
        private UUID intakeId;
        private UUID roleId;
        private UUID moduleId;
        private UUID componentId;
        private UUID userId;

        NotificationDestinationBuilder() {
        }

        public NotificationDestinationBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public NotificationDestinationBuilder roleId(UUID roleId) {
            this.roleId = roleId;
            return this;
        }

        public NotificationDestinationBuilder moduleId(UUID moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public NotificationDestinationBuilder componentId(UUID componentId) {
            this.componentId = componentId;
            return this;
        }

        public NotificationDestinationBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public NotificationDestination build() {
            return new NotificationDestination(this.intakeId, this.roleId, this.moduleId, this.componentId, this.userId);
        }

        public String toString() {
            return "NotificationDestination.NotificationDestinationBuilder(intakeId=" + this.intakeId + ", roleId=" + this.roleId + ", moduleId=" + this.moduleId + ", componentId=" + this.componentId + ", userId=" + this.userId + ")";
        }
    }
}
