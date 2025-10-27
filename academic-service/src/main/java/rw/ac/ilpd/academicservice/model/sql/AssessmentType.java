package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_assessment_types")
@EntityListeners(AuditingEntityListener.class)
public class AssessmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //    @Enumerated(EnumType.STRING)
//    private AssessmentTitle title;
    //@Column(length =40)
    @Length(max = 40)
    private String title;

    //@Column(length = 20)
    @Length(max = 10)
    private String acronym;

    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = AssessmentGroup.class,optional = false)
    private AssessmentGroup assessmentGroup;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private boolean isDeleted;


    public AssessmentType(UUID id, String title, String acronym, BigDecimal weight, AssessmentGroup assessmentGroup, LocalDateTime createdAt, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.acronym = acronym;
        this.weight = weight;
        this.assessmentGroup = assessmentGroup;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public AssessmentType() {
    }

    public static AssessmentTypeBuilder builder() {
        return new AssessmentTypeBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public AssessmentGroup getAssessmentGroup() {
        return this.assessmentGroup;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setAssessmentGroup(AssessmentGroup assessmentGroup) {
        this.assessmentGroup = assessmentGroup;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String toString() {
        return "AssessmentType(id=" + this.getId() + ", title=" + this.getTitle() + ", acronym=" + this.getAcronym() + ", weight=" + this.getWeight() + ", assessmentGroup=" + this.getAssessmentGroup() + ", createdAt=" + this.getCreatedAt() + ", isDeleted=" + this.isDeleted() + ")";
    }

    public static class AssessmentTypeBuilder {
        private UUID id;
        private String title;
        private String acronym;
        private BigDecimal weight;
        private AssessmentGroup assessmentGroup;
        private LocalDateTime createdAt;
        private boolean isDeleted;

        AssessmentTypeBuilder() {
        }

        public AssessmentTypeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AssessmentTypeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AssessmentTypeBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public AssessmentTypeBuilder weight(BigDecimal weight) {
            this.weight = weight;
            return this;
        }

        public AssessmentTypeBuilder assessmentGroup(AssessmentGroup assessmentGroup) {
            this.assessmentGroup = assessmentGroup;
            return this;
        }

        public AssessmentTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AssessmentTypeBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public AssessmentType build() {
            return new AssessmentType(this.id, this.title, this.acronym, this.weight, this.assessmentGroup, this.createdAt, this.isDeleted);
        }

        public String toString() {
            return "AssessmentType.AssessmentTypeBuilder(id=" + this.id + ", title=" + this.title + ", acronym=" + this.acronym + ", weight=" + this.weight + ", assessmentGroup=" + this.assessmentGroup + ", createdAt=" + this.createdAt + ", isDeleted=" + this.isDeleted + ")";
        }
    }
}
