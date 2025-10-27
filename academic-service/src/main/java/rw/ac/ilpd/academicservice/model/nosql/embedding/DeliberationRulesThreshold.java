package rw.ac.ilpd.academicservice.model.nosql.embedding;


import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeliberationRulesThreshold {
    private String id = UUID.randomUUID().toString();
    private String key;
    private Double value;
    @CreatedDate
    private LocalDateTime createdAt;

    public DeliberationRulesThreshold(String id, String key, Double value, LocalDateTime createdAt) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.createdAt = createdAt;
    }

    public DeliberationRulesThreshold() {
    }

    public static DeliberationRulesThresholdBuilder builder() {
        return new DeliberationRulesThresholdBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public Double getValue() {
        return this.value;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class DeliberationRulesThresholdBuilder {
        private String id;
        private String key;
        private Double value;
        private LocalDateTime createdAt;

        DeliberationRulesThresholdBuilder() {
        }

        public DeliberationRulesThresholdBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DeliberationRulesThresholdBuilder key(String key) {
            this.key = key;
            return this;
        }

        public DeliberationRulesThresholdBuilder value(Double value) {
            this.value = value;
            return this;
        }

        public DeliberationRulesThresholdBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DeliberationRulesThreshold build() {
            return new DeliberationRulesThreshold(this.id, this.key, this.value, this.createdAt);
        }

        public String toString() {
            return "DeliberationRulesThreshold.DeliberationRulesThresholdBuilder(id=" + this.id + ", key=" + this.key + ", value=" + this.value + ", createdAt=" + this.createdAt + ")";
        }
    }
}
