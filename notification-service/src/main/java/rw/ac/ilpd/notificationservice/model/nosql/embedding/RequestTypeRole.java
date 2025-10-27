package rw.ac.ilpd.notificationservice.model.nosql.embedding;

import rw.ac.ilpd.sharedlibrary.enums.RequestPartyType;

import java.util.UUID;

public class RequestTypeRole {

    private UUID roleId;

    private RequestPartyType party;

    private Integer priority;

    public RequestTypeRole(UUID roleId, RequestPartyType party, Integer priority) {
        this.roleId = roleId;
        this.party = party;
        this.priority = priority;
    }

    public RequestTypeRole() {
    }

    public static RequestTypeRoleBuilder builder() {
        return new RequestTypeRoleBuilder();
    }

    public UUID getRoleId() {
        return this.roleId;
    }

    public RequestPartyType getParty() {
        return this.party;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public void setParty(RequestPartyType party) {
        this.party = party;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public static class RequestTypeRoleBuilder {
        private UUID roleId;
        private RequestPartyType party;
        private Integer priority;

        RequestTypeRoleBuilder() {
        }

        public RequestTypeRoleBuilder roleId(UUID roleId) {
            this.roleId = roleId;
            return this;
        }

        public RequestTypeRoleBuilder party(RequestPartyType party) {
            this.party = party;
            return this;
        }

        public RequestTypeRoleBuilder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public RequestTypeRole build() {
            return new RequestTypeRole(this.roleId, this.party, this.priority);
        }

        public String toString() {
            return "RequestTypeRole.RequestTypeRoleBuilder(roleId=" + this.roleId + ", party=" + this.party + ", priority=" + this.priority + ")";
        }
    }
}
