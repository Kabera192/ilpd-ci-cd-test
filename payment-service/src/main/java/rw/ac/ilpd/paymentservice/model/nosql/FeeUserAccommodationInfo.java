package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "payment_fee_user_accommodation_infos")
public class FeeUserAccommodationInfo {
    @Id
    private String id;
    private UUID feeUserId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID reservationId;
    @CreatedDate
    private LocalDateTime createAt;

    public FeeUserAccommodationInfo(String id, UUID feeUserId, LocalDateTime startDate, LocalDateTime endDate, UUID reservationId, LocalDateTime createAt) {
        this.id = id;
        this.feeUserId = feeUserId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationId = reservationId;
        this.createAt = createAt;
    }

    public FeeUserAccommodationInfo() {
    }

    public static FeeUserAccommodationInfoBuilder builder() {
        return new FeeUserAccommodationInfoBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getFeeUserId() {
        return this.feeUserId;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public UUID getReservationId() {
        return this.reservationId;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFeeUserId(UUID feeUserId) {
        this.feeUserId = feeUserId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public static class FeeUserAccommodationInfoBuilder {
        private String id;
        private UUID feeUserId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private UUID reservationId;
        private LocalDateTime createAt;

        FeeUserAccommodationInfoBuilder() {
        }

        public FeeUserAccommodationInfoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FeeUserAccommodationInfoBuilder feeUserId(UUID feeUserId) {
            this.feeUserId = feeUserId;
            return this;
        }

        public FeeUserAccommodationInfoBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public FeeUserAccommodationInfoBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public FeeUserAccommodationInfoBuilder reservationId(UUID reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public FeeUserAccommodationInfoBuilder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public FeeUserAccommodationInfo build() {
            return new FeeUserAccommodationInfo(this.id, this.feeUserId, this.startDate, this.endDate, this.reservationId, this.createAt);
        }

        public String toString() {
            return "FeeUserAccommodationInfo.FeeUserAccommodationInfoBuilder(id=" + this.id + ", feeUserId=" + this.feeUserId + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", reservationId=" + this.reservationId + ", createAt=" + this.createAt + ")";
        }
    }
}
