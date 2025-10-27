package rw.ac.ilpd.registrationservice.model.nosql.embedding;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * This entity represents recommender information embedded directly
 * within academic background records, eliminating the need for external references.
 *
 * REPLACES: RecommenderUniversity which only stored IDs
 */
public class EmbeddedRecommender {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private LocalDateTime createdAt;

    // Constructors
    public EmbeddedRecommender() {
        this.createdAt = LocalDateTime.now();
    }

    public EmbeddedRecommender(String id, String firstName, String lastName,
                               String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "EmbeddedRecommender{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
