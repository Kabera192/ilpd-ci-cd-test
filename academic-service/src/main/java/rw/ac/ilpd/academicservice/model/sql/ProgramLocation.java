package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_program_locations")
public class ProgramLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Program program;

    private String locationId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ProgramLocation(UUID id, Program program, String locationId, LocalDateTime createdAt) {
        this.id = id;
        this.program = program;
        this.locationId = locationId;
        this.createdAt = createdAt;
    }

    public ProgramLocation() {
    }

    public static ProgramLocationBuilder builder() {
        return new ProgramLocationBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Program getProgram() {
        return this.program;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProgramLocation)) return false;
        final ProgramLocation other = (ProgramLocation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$program = this.getProgram();
        final Object other$program = other.getProgram();
        if (this$program == null ? other$program != null : !this$program.equals(other$program)) return false;
        final Object this$locationId = this.getLocationId();
        final Object other$locationId = other.getLocationId();
        if (this$locationId == null ? other$locationId != null : !this$locationId.equals(other$locationId))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProgramLocation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $program = this.getProgram();
        result = result * PRIME + ($program == null ? 43 : $program.hashCode());
        final Object $locationId = this.getLocationId();
        result = result * PRIME + ($locationId == null ? 43 : $locationId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ProgramLocation(id=" + this.getId() + ", program=" + this.getProgram() + ", locationId=" + this.getLocationId() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ProgramLocationBuilder {
        private UUID id;
        private Program program;
        private String locationId;
        private LocalDateTime createdAt;

        ProgramLocationBuilder() {
        }

        public ProgramLocationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ProgramLocationBuilder program(Program program) {
            this.program = program;
            return this;
        }

        public ProgramLocationBuilder locationId(String locationId) {
            this.locationId = locationId;
            return this;
        }

        public ProgramLocationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProgramLocation build() {
            return new ProgramLocation(this.id, this.program, this.locationId, this.createdAt);
        }

        public String toString() {
            return "ProgramLocation.ProgramLocationBuilder(id=" + this.id + ", program=" + this.program + ", locationId=" + this.locationId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
