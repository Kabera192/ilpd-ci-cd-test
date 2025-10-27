package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.EmbeddedRecommender;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This entity represents an applicant's academic background information.
 * Updated to support user-centric architecture where multiple academic backgrounds
 * can be associated with a single user, rather than one per application.
 * <p>
 * BREAKING CHANGE: Changed from applicationId to userId approach with embedded recommender data
 **/
@Document(collection = "reg_academic_background")
public class AcademicBackground {

    @Id
    private String id;
    private UUID userId;

    private University universityId;

    private List<EmbeddedRecommender> recommenders;

    private String degree;

    private LocalDate startDate;

    private LocalDate endDate;

    // Added timestamps for better tracking
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public AcademicBackground() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public AcademicBackground(String id, UUID userId, University universityId, List<EmbeddedRecommender> recommenders
            , String degree, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.universityId = universityId;
        this.recommenders = recommenders;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters with automatic updatedAt handling
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.updatedAt = LocalDateTime.now();
    }

    public University getUniversityId() {
        return universityId;
    }

    public void setUniversityId(University universityId) {
        this.universityId = universityId;
        this.updatedAt = LocalDateTime.now();
    }

    public List<EmbeddedRecommender> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<EmbeddedRecommender> recommenders) {
        this.recommenders = recommenders;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AcademicBackground: {id: " + id + ", userId: " + userId + ", universityId: " + universityId + ", " +
                "recommenders: " + recommenders + ", degree: " + degree + ", startDate: " + startDate + ", endDate: " + endDate + ", createdAt: " + createdAt + ", updatedAt: " + updatedAt + "}";
    }
}