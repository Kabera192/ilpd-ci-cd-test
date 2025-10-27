package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_intake_coordinators")
public class IntakeCoordinator {

    @Id
    private UUID id;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;
    @Column(name = "from_date")
    private LocalDateTime from;

    @Column(name = "to_date", nullable = true)
    private LocalDateTime to;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public IntakeCoordinator() {}

    public IntakeCoordinator(UUID id, UUID userId, Intake intake, LocalDateTime from, LocalDateTime to, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        this.id = id;
        this.userId = userId;
        this.intake = intake;
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public Intake getIntake()
    {
        return intake;
    }

    public void setIntake(Intake intake)
    {
        this.intake = intake;
    }

    public LocalDateTime getFrom()
    {
        return from;
    }

    public void setFrom(LocalDateTime from)
    {
        this.from = from;
    }

    public LocalDateTime getTo()
    {
        return to;
    }

    public void setTo(LocalDateTime to)
    {
        this.to = to;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString()
    {
        return "IntakeCoordinator{" +
                "id: " + id +
                ", userId: " + userId +
                ", intake: " + intake +
                ", from: " + from +
                ", to: " + to +
                ", createdAt: " + createdAt +
                ", updatedAt: " + updatedAt +
                '}';
    }

    public static final class IntakeCoordinatorBuilder
    {
        private UUID id;
        private UUID userId;
        private Intake intake;
        private LocalDateTime from;
        private LocalDateTime to;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public IntakeCoordinatorBuilder()
        {
        }

        public IntakeCoordinatorBuilder(IntakeCoordinator other)
        {
            this.id = other.id;
            this.userId = other.userId;
            this.intake = other.intake;
            this.from = other.from;
            this.to = other.to;
            this.createdAt = other.createdAt;
            this.updatedAt = other.updatedAt;
        }

        public static IntakeCoordinatorBuilder anIntakeCoordinator()
        {
            return new IntakeCoordinatorBuilder();
        }

        public IntakeCoordinatorBuilder id(UUID id)
        {
            this.id = id;
            return this;
        }

        public IntakeCoordinatorBuilder userId(UUID userId)
        {
            this.userId = userId;
            return this;
        }

        public IntakeCoordinatorBuilder intake(Intake intake)
        {
            this.intake = intake;
            return this;
        }

        public IntakeCoordinatorBuilder from(LocalDateTime from)
        {
            this.from = from;
            return this;
        }

        public IntakeCoordinatorBuilder to(LocalDateTime to)
        {
            this.to = to;
            return this;
        }

        public IntakeCoordinatorBuilder createdAt(LocalDateTime createdAt)
        {
            this.createdAt = createdAt;
            return this;
        }

        public IntakeCoordinatorBuilder updatedAt(LocalDateTime updatedAt)
        {
            this.updatedAt = updatedAt;
            return this;
        }

        public IntakeCoordinator build()
        {
            IntakeCoordinator intakeCoordinator = new IntakeCoordinator();
            intakeCoordinator.setId(id);
            intakeCoordinator.setUserId(userId);
            intakeCoordinator.setIntake(intake);
            intakeCoordinator.setFrom(from);
            intakeCoordinator.setTo(to);
            intakeCoordinator.setCreatedAt(createdAt);
            intakeCoordinator.setUpdatedAt(updatedAt);
            return intakeCoordinator;
        }
    }
}
