package rw.ac.ilpd.academicservice.model.nosql.embedding;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

public class DeliberationDistinction {
    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private BigDecimal minScore;

    private BigDecimal maxScore;


    public DeliberationDistinction(String name, BigDecimal minScore, BigDecimal maxScore) {
        this.name = name;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public DeliberationDistinction() {
    }

    public static DeliberationDistinctionBuilder builder() {
        return new DeliberationDistinctionBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getMinScore() {
        return this.minScore;
    }

    public BigDecimal getMaxScore() {
        return this.maxScore;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinScore(BigDecimal minScore) {
        this.minScore = minScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DeliberationDistinction)) return false;
        final DeliberationDistinction other = (DeliberationDistinction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$minScore = this.getMinScore();
        final Object other$minScore = other.getMinScore();
        if (this$minScore == null ? other$minScore != null : !this$minScore.equals(other$minScore)) return false;
        final Object this$maxScore = this.getMaxScore();
        final Object other$maxScore = other.getMaxScore();
        if (this$maxScore == null ? other$maxScore != null : !this$maxScore.equals(other$maxScore)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DeliberationDistinction;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $minScore = this.getMinScore();
        result = result * PRIME + ($minScore == null ? 43 : $minScore.hashCode());
        final Object $maxScore = this.getMaxScore();
        result = result * PRIME + ($maxScore == null ? 43 : $maxScore.hashCode());
        return result;
    }

    public String toString() {
        return "DeliberationDistinction(id=" + this.getId() + ", name=" + this.getName() + ", minScore=" + this.getMinScore() + ", maxScore=" + this.getMaxScore() + ")";
    }

    public static class DeliberationDistinctionBuilder {
        private String id;
        private String name;
        private BigDecimal minScore;
        private BigDecimal maxScore;

        DeliberationDistinctionBuilder() {
        }

        public DeliberationDistinctionBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DeliberationDistinctionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DeliberationDistinctionBuilder minScore(BigDecimal minScore) {
            this.minScore = minScore;
            return this;
        }

        public DeliberationDistinctionBuilder maxScore(BigDecimal maxScore) {
            this.maxScore = maxScore;
            return this;
        }

        public DeliberationDistinction build() {
            return new DeliberationDistinction( this.name, this.minScore, this.maxScore);
        }

        public String toString() {
            return "DeliberationDistinction.DeliberationDistinctionBuilder(id=" + this.id + ", name=" + this.name + ", minScore=" + this.minScore + ", maxScore=" + this.maxScore + ")";
        }
    }
}
