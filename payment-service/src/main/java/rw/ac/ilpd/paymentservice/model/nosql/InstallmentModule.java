package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "payment_installment_modules")
public class InstallmentModule {
    @Id
    private String id;
    private String installmentId;
    private UUID curriculumModuleId;

    public InstallmentModule(String id, String installmentId, UUID curriculumModuleId) {
        this.id = id;
        this.installmentId = installmentId;
        this.curriculumModuleId = curriculumModuleId;
    }

    public InstallmentModule() {
    }

    public static InstallmentModuleBuilder builder() {
        return new InstallmentModuleBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getInstallmentId() {
        return this.installmentId;
    }

    public UUID getCurriculumModuleId() {
        return this.curriculumModuleId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInstallmentId(String installmentId) {
        this.installmentId = installmentId;
    }

    public void setCurriculumModuleId(UUID curriculumModuleId) {
        this.curriculumModuleId = curriculumModuleId;
    }

    public static class InstallmentModuleBuilder {
        private String id;
        private String installmentId;
        private UUID curriculumModuleId;

        InstallmentModuleBuilder() {
        }

        public InstallmentModuleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InstallmentModuleBuilder installmentId(String installmentId) {
            this.installmentId = installmentId;
            return this;
        }

        public InstallmentModuleBuilder curriculumModuleId(UUID curriculumModuleId) {
            this.curriculumModuleId = curriculumModuleId;
            return this;
        }

        public InstallmentModule build() {
            return new InstallmentModule(this.id, this.installmentId, this.curriculumModuleId);
        }

        public String toString() {
            return "InstallmentModule.InstallmentModuleBuilder(id=" + this.id + ", installmentId=" + this.installmentId + ", curriculumModuleId=" + this.curriculumModuleId + ")";
        }
    }
}
