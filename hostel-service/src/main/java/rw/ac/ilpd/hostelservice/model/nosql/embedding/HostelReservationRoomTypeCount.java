package rw.ac.ilpd.hostelservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public class HostelReservationRoomTypeCount {
    private String id = UUID.randomUUID().toString();
    private String hostelReservationId;
    private String hostelRoomTypePricingId;
    private int numberOfRooms;
    @CreatedDate
    private LocalDateTime createdAt;

    public HostelReservationRoomTypeCount(String id, String hostelReservationId, String hostelRoomTypePricingId, int numberOfRooms, LocalDateTime createdAt) {
        this.id = id;
        this.hostelReservationId = hostelReservationId;
        this.hostelRoomTypePricingId = hostelRoomTypePricingId;
        this.numberOfRooms = numberOfRooms;
        this.createdAt = createdAt;
    }

    public HostelReservationRoomTypeCount() {
    }

    private static String $default$id() {
        return UUID.randomUUID().toString();
    }

    public static HostelReservationRoomTypeCountBuilder builder() {
        return new HostelReservationRoomTypeCountBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getHostelReservationId() {
        return this.hostelReservationId;
    }

    public String getHostelRoomTypePricingId() {
        return this.hostelRoomTypePricingId;
    }

    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHostelReservationId(String hostelReservationId) {
        this.hostelReservationId = hostelReservationId;
    }

    public void setHostelRoomTypePricingId(String hostelRoomTypePricingId) {
        this.hostelRoomTypePricingId = hostelRoomTypePricingId;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "HostelReservationRoomTypeCount(id=" + this.getId() + ", hostelReservationId=" + this.getHostelReservationId() + ", hostelRoomTypePricingId=" + this.getHostelRoomTypePricingId() + ", numberOfRooms=" + this.getNumberOfRooms() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class HostelReservationRoomTypeCountBuilder {
        private String id$value;
        private boolean id$set;
        private String hostelReservationId;
        private String hostelRoomTypePricingId;
        private int numberOfRooms;
        private LocalDateTime createdAt;

        HostelReservationRoomTypeCountBuilder() {
        }

        public HostelReservationRoomTypeCountBuilder id(String id) {
            this.id$value = id;
            this.id$set = true;
            return this;
        }

        public HostelReservationRoomTypeCountBuilder hostelReservationId(String hostelReservationId) {
            this.hostelReservationId = hostelReservationId;
            return this;
        }

        public HostelReservationRoomTypeCountBuilder hostelRoomTypePricingId(String hostelRoomTypePricingId) {
            this.hostelRoomTypePricingId = hostelRoomTypePricingId;
            return this;
        }

        public HostelReservationRoomTypeCountBuilder numberOfRooms(int numberOfRooms) {
            this.numberOfRooms = numberOfRooms;
            return this;
        }

        public HostelReservationRoomTypeCountBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelReservationRoomTypeCount build() {
            String id$value = this.id$value;
            if (!this.id$set) {
                id$value = HostelReservationRoomTypeCount.$default$id();
            }
            return new HostelReservationRoomTypeCount(id$value, this.hostelReservationId, this.hostelRoomTypePricingId, this.numberOfRooms, this.createdAt);
        }

        public String toString() {
            return "HostelReservationRoomTypeCount.HostelReservationRoomTypeCountBuilder(id$value=" + this.id$value + ", hostelReservationId=" + this.hostelReservationId + ", hostelRoomTypePricingId=" + this.hostelRoomTypePricingId + ", numberOfRooms=" + this.numberOfRooms + ", createdAt=" + this.createdAt + ")";
        }
    }
}
