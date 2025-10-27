package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aca_lecturer_component_materials")
public class LecturerComponentMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CourseMaterial courseMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    private LecturerComponent lecturerComponent;

    public LecturerComponentMaterial(UUID id, CourseMaterial courseMaterial, LecturerComponent lecturerComponent) {
        this.id = id;
        this.courseMaterial = courseMaterial;
        this.lecturerComponent = lecturerComponent;
    }

    public LecturerComponentMaterial() {
    }

    public static LecturerComponentMaterialBuilder builder() {
        return new LecturerComponentMaterialBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public CourseMaterial getCourseMaterial() {
        return this.courseMaterial;
    }

    public LecturerComponent getLecturerComponent() {
        return this.lecturerComponent;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCourseMaterial(CourseMaterial courseMaterial) {
        this.courseMaterial = courseMaterial;
    }

    public void setLecturerComponent(LecturerComponent lecturerComponent) {
        this.lecturerComponent = lecturerComponent;
    }

    public String toString() {
        return "LecturerComponentMaterial(id=" + this.getId() + ", courseMaterial=" + this.getCourseMaterial() + ", lecturerComponent=" + this.getLecturerComponent() + ")";
    }

    public static class LecturerComponentMaterialBuilder {
        private UUID id;
        private CourseMaterial courseMaterial;
        private LecturerComponent lecturerComponent;

        LecturerComponentMaterialBuilder() {
        }

        public LecturerComponentMaterialBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LecturerComponentMaterialBuilder courseMaterial(CourseMaterial courseMaterial) {
            this.courseMaterial = courseMaterial;
            return this;
        }

        public LecturerComponentMaterialBuilder lecturerComponent(LecturerComponent lecturerComponent) {
            this.lecturerComponent = lecturerComponent;
            return this;
        }

        public LecturerComponentMaterial build() {
            return new LecturerComponentMaterial(this.id, this.courseMaterial, this.lecturerComponent);
        }

        public String toString() {
            return "LecturerComponentMaterial.LecturerComponentMaterialBuilder(id=" + this.id + ", courseMaterial=" + this.courseMaterial + ", lecturerComponent=" + this.lecturerComponent + ")";
        }
    }
}