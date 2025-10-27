/*
* This entity defines various types of requests of such as request
* for items, request to suspend, request for retake a module and so on.
* */
package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestTypeRole;

import java.util.List;

@Document(collection = "notif_request_types")
public class RequestType {
    @Id
    private String id;

    private String name;

    private Boolean deletedStatus;

    private Boolean needsPayment;

    private List<RequestTypeRole> roles;

    public RequestType(String id, String name, Boolean deletedStatus, Boolean needsPayment, List<RequestTypeRole> roles) {
        this.id = id;
        this.name = name;
        this.deletedStatus = deletedStatus;
        this.needsPayment = needsPayment;
        this.roles = roles;
    }

    public RequestType() {
    }

    public static RequestTypeBuilder builder() {
        return new RequestTypeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getDeletedStatus() {
        return this.deletedStatus;
    }

    public Boolean getNeedsPayment() {
        return this.needsPayment;
    }

    public List<RequestTypeRole> getRoles() {
        return this.roles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public void setNeedsPayment(Boolean needsPayment) {
        this.needsPayment = needsPayment;
    }

    public void setRoles(List<RequestTypeRole> roles) {
        this.roles = roles;
    }

    public static class RequestTypeBuilder {
        private String id;
        private String name;
        private Boolean deletedStatus;
        private Boolean needsPayment;
        private List<RequestTypeRole> roles;

        RequestTypeBuilder() {
        }

        public RequestTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RequestTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RequestTypeBuilder deletedStatus(Boolean deletedStatus) {
            this.deletedStatus = deletedStatus;
            return this;
        }

        public RequestTypeBuilder needsPayment(Boolean needsPayment) {
            this.needsPayment = needsPayment;
            return this;
        }

        public RequestTypeBuilder roles(List<RequestTypeRole> roles) {
            this.roles = roles;
            return this;
        }

        public RequestType build() {
            return new RequestType(this.id, this.name, this.deletedStatus, this.needsPayment, this.roles);
        }

        public String toString() {
            return "RequestType.RequestTypeBuilder(id=" + this.id + ", name=" + this.name + ", deletedStatus=" + this.deletedStatus + ", needsPayment=" + this.needsPayment + ", roles=" + this.roles + ")";
        }
    }
}
