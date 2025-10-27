package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_lecturer_components")
public class LecturerComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Component component;

    private Boolean activeStatus = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public LecturerComponent(UUID id, Lecturer lecturer, Component component, Boolean activeStatus, LocalDateTime createdAt) {
        this.id = id;
        this.lecturer = lecturer;
        this.component = component;
        this.activeStatus = activeStatus;
        this.createdAt = createdAt;
    }

    public LecturerComponent() {
    }

    public static LecturerComponentBuilder builder() {
        return new LecturerComponentBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Lecturer getLecturer() {
        return this.lecturer;
    }

    public Component getComponent() {
        return this.component;
    }

    public Boolean getActiveStatus() {
        return this.activeStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LecturerComponent)) return false;
        final LecturerComponent other = (LecturerComponent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$lecturer = this.getLecturer();
        final Object other$lecturer = other.getLecturer();
        if (this$lecturer == null ? other$lecturer != null : !this$lecturer.equals(other$lecturer)) return false;
        final Object this$component = this.getComponent();
        final Object other$component = other.getComponent();
        if (this$component == null ? other$component != null : !this$component.equals(other$component)) return false;
        final Object this$activeStatus = this.getActiveStatus();
        final Object other$activeStatus = other.getActiveStatus();
        if (this$activeStatus == null ? other$activeStatus != null : !this$activeStatus.equals(other$activeStatus))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LecturerComponent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $lecturer = this.getLecturer();
        result = result * PRIME + ($lecturer == null ? 43 : $lecturer.hashCode());
        final Object $component = this.getComponent();
        result = result * PRIME + ($component == null ? 43 : $component.hashCode());
        final Object $activeStatus = this.getActiveStatus();
        result = result * PRIME + ($activeStatus == null ? 43 : $activeStatus.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "LecturerComponent(id=" + this.getId() + ", lecturer=" + this.getLecturer() + ", component=" + this.getComponent() + ", activeStatus=" + this.getActiveStatus() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class LecturerComponentBuilder {
        private UUID id;
        private Lecturer lecturer;
        private Component component;
        private Boolean activeStatus;
        private LocalDateTime createdAt;

        LecturerComponentBuilder() {
        }

        public LecturerComponentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LecturerComponentBuilder lecturer(Lecturer lecturer) {
            this.lecturer = lecturer;
            return this;
        }

        public LecturerComponentBuilder component(Component component) {
            this.component = component;
            return this;
        }

        public LecturerComponentBuilder activeStatus(Boolean activeStatus) {
            this.activeStatus = activeStatus;
            return this;
        }

        public LecturerComponentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public LecturerComponent build() {
            return new LecturerComponent(this.id, this.lecturer, this.component, this.activeStatus, this.createdAt);
        }

        public String toString() {
            return "LecturerComponent.LecturerComponentBuilder(id=" + this.id + ", lecturer=" + this.lecturer + ", component=" + this.component + ", activeStatus=" + this.activeStatus + ", createdAt=" + this.createdAt + ")";
        }
    }
}
