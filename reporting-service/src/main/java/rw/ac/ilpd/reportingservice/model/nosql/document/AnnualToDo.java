package rw.ac.ilpd.reportingservice.model.nosql.document;

import jakarta.validation.constraints.DecimalMin;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "report_annual_todos")
public class AnnualToDo {
    //    @Id
//    private String id;
//    private String requestId;
//    private  String description;
//    private BigDecimal cost;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private ToDoStatus status;
//    private UUID unitId;
//    private LocalDateTime createdAt;
    @Id
    private String id;

    @Indexed
    private String requestId;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal cost;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    private ToDoStatus status = ToDoStatus.PENDING;

    @Indexed
    private UUID unitId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    private boolean active = true;

    private String notes;
    private Integer priority; // 1-5, where 1 is highest priority
    private String assignedTo;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private BigDecimal actualCost;
    private boolean isDeleted;

    public AnnualToDo(String id, String requestId, String description, @DecimalMin(value = "0.0", inclusive = false) BigDecimal cost, LocalDate startDate, LocalDate endDate, ToDoStatus status, UUID unitId, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, boolean active, String notes, Integer priority, String assignedTo, LocalDate actualStartDate, LocalDate actualEndDate, BigDecimal actualCost, boolean isDeleted) {
        this.id = id;
        this.requestId = requestId;
        this.description = description;
        this.cost = cost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.unitId = unitId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.active = active;
        this.notes = notes;
        this.priority = priority;
        this.assignedTo = assignedTo;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
        this.actualCost = actualCost;
        this.isDeleted = isDeleted;
    }

    public AnnualToDo() {
    }

    private static ToDoStatus $default$status() {
        return ToDoStatus.PENDING;
    }

    private static boolean $default$active() {
        return true;
    }

    public static AnnualToDoBuilder builder() {
        return new AnnualToDoBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getDescription() {
        return this.description;
    }

    public @DecimalMin(value = "0.0", inclusive = false) BigDecimal getCost() {
        return this.cost;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public ToDoStatus getStatus() {
        return this.status;
    }

    public UUID getUnitId() {
        return this.unitId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public boolean isActive() {
        return this.active;
    }

    public String getNotes() {
        return this.notes;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public LocalDate getActualStartDate() {
        return this.actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return this.actualEndDate;
    }

    public BigDecimal getActualCost() {
        return this.actualCost;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(@DecimalMin(value = "0.0", inclusive = false) BigDecimal cost) {
        this.cost = cost;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static class AnnualToDoBuilder {
        private String id;
        private String requestId;
        private String description;
        private @DecimalMin(value = "0.0", inclusive = false) BigDecimal cost;
        private LocalDate startDate;
        private LocalDate endDate;
        private ToDoStatus status$value;
        private boolean status$set;
        private UUID unitId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private boolean active$value;
        private boolean active$set;
        private String notes;
        private Integer priority;
        private String assignedTo;
        private LocalDate actualStartDate;
        private LocalDate actualEndDate;
        private BigDecimal actualCost;
        private boolean isDeleted;

        AnnualToDoBuilder() {
        }

        public AnnualToDoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AnnualToDoBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public AnnualToDoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AnnualToDoBuilder cost(@DecimalMin(value = "0.0", inclusive = false) BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public AnnualToDoBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public AnnualToDoBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public AnnualToDoBuilder status(ToDoStatus status) {
            this.status$value = status;
            this.status$set = true;
            return this;
        }

        public AnnualToDoBuilder unitId(UUID unitId) {
            this.unitId = unitId;
            return this;
        }

        public AnnualToDoBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AnnualToDoBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public AnnualToDoBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public AnnualToDoBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public AnnualToDoBuilder active(boolean active) {
            this.active$value = active;
            this.active$set = true;
            return this;
        }

        public AnnualToDoBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public AnnualToDoBuilder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public AnnualToDoBuilder assignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
            return this;
        }

        public AnnualToDoBuilder actualStartDate(LocalDate actualStartDate) {
            this.actualStartDate = actualStartDate;
            return this;
        }

        public AnnualToDoBuilder actualEndDate(LocalDate actualEndDate) {
            this.actualEndDate = actualEndDate;
            return this;
        }

        public AnnualToDoBuilder actualCost(BigDecimal actualCost) {
            this.actualCost = actualCost;
            return this;
        }

        public AnnualToDoBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public AnnualToDo build() {
            ToDoStatus status$value = this.status$value;
            if (!this.status$set) {
                status$value = AnnualToDo.$default$status();
            }
            boolean active$value = this.active$value;
            if (!this.active$set) {
                active$value = AnnualToDo.$default$active();
            }
            return new AnnualToDo(this.id, this.requestId, this.description, this.cost, this.startDate, this.endDate, status$value, this.unitId, this.createdAt, this.updatedAt, this.createdBy, this.updatedBy, active$value, this.notes, this.priority, this.assignedTo, this.actualStartDate, this.actualEndDate, this.actualCost, this.isDeleted);
        }

        public String toString() {
            return "AnnualToDo.AnnualToDoBuilder(id=" + this.id + ", requestId=" + this.requestId + ", description=" + this.description + ", cost=" + this.cost + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", status$value=" + this.status$value + ", unitId=" + this.unitId + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", createdBy=" + this.createdBy + ", updatedBy=" + this.updatedBy + ", active$value=" + this.active$value + ", notes=" + this.notes + ", priority=" + this.priority + ", assignedTo=" + this.assignedTo + ", actualStartDate=" + this.actualStartDate + ", actualEndDate=" + this.actualEndDate + ", actualCost=" + this.actualCost + ", isDeleted=" + this.isDeleted + ")";
        }
    }
}
