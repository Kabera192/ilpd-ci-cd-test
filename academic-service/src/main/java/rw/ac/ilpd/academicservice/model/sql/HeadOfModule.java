package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_head_of_modules")
@EntityListeners(AuditingEntityListener.class)
public class HeadOfModule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;

    @CreatedDate
    @Column(updatable = false, nullable = false, name = "from_date")
    private LocalDateTime from;
    @Column(name = "to_date")
    private LocalDateTime to;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public HeadOfModule(UUID id, Lecturer lecturer, Module module, LocalDateTime from, LocalDateTime to, LocalDateTime createdAt) {
        this.id = id;
        this.lecturer = lecturer;
        this.module = module;
        this.from = from;
        this.to = to;
        this.createdAt = createdAt;
    }

    public HeadOfModule() {
    }

    public static HeadOfModuleBuilder builder() {
        return new HeadOfModuleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Lecturer getLecturer() {
        return this.lecturer;
    }

    public Module getModule() {
        return this.module;
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return this.to;
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

    public void setModule(Module module) {
        this.module = module;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "HeadOfModule(id=" + this.getId() + ", lecturer=" + this.getLecturer() + ", module=" + this.getModule() + ", from=" + this.getFrom() + ", to=" + this.getTo() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class HeadOfModuleBuilder {
        private UUID id;
        private Lecturer lecturer;
        private Module module;
        private LocalDateTime from;
        private LocalDateTime to;
        private LocalDateTime createdAt;

        HeadOfModuleBuilder() {
        }

        public HeadOfModuleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HeadOfModuleBuilder lecturer(Lecturer lecturer) {
            this.lecturer = lecturer;
            return this;
        }

        public HeadOfModuleBuilder module(Module module) {
            this.module = module;
            return this;
        }

        public HeadOfModuleBuilder from(LocalDateTime from) {
            this.from = from;
            return this;
        }

        public HeadOfModuleBuilder to(LocalDateTime to) {
            this.to = to;
            return this;
        }

        public HeadOfModuleBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HeadOfModule build() {
            return new HeadOfModule(this.id, this.lecturer, this.module, this.from, this.to, this.createdAt);
        }

        public String toString() {
            return "HeadOfModule.HeadOfModuleBuilder(id=" + this.id + ", lecturer=" + this.lecturer + ", module=" + this.module + ", from=" + this.from + ", to=" + this.to + ", createdAt=" + this.createdAt + ")";
        }
    }
}
