package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "reg_application")
public class Application {

    @Id
    private String id;
    private UUID userId;
    private UUID intakeId;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private List<String> applicationDocuments; // Keep existing field name

    // Default constructor
    public Application() {
        this.createdAt = LocalDateTime.now();
        this.status = ApplicationStatus.PENDING;
    }

    // Main constructor without nextOfKinId
    public Application(String id, UUID userId, UUID intakeId, ApplicationStatus status,
                       LocalDateTime createdAt, List<String> applicationDocuments) {
        this.id = id;
        this.userId = userId;
        this.intakeId = intakeId;
        this.status = status;
        this.createdAt = createdAt;
        this.applicationDocuments = applicationDocuments;
    }

    // Getters and Setters (removed nextOfKinId getter/setter)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getIntakeId() { return intakeId; }
    public void setIntakeId(UUID intakeId) { this.intakeId = intakeId; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getApplicationDocuments() { return applicationDocuments; }
    public void setApplicationDocuments(List<String> applicationDocuments) { this.applicationDocuments = applicationDocuments; }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", intakeId=" + intakeId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", applicationDocuments=" + applicationDocuments +
                '}';
    }
}