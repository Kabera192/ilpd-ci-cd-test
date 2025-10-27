package rw.ac.ilpd.hostelservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "hostel_hostel_reservation_rooms")
public class HostelReservationRoom {
    @Id
    private UUID id;
    private String randomCodeInfoFill;
    //    private  UUID reservationId;
    private UUID hostelReservationRoomTypeCountId;
    private UUID roomId;
    /*Client id reference to External user table*/
    private UUID clientId;
    private BigDecimal actualPrice;
    private LocalDateTime actualDate;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public HostelReservationRoom(UUID id, String randomCodeInfoFill, UUID hostelReservationRoomTypeCountId, UUID roomId, UUID clientId, BigDecimal actualPrice, LocalDateTime actualDate, LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.randomCodeInfoFill = randomCodeInfoFill;
        this.hostelReservationRoomTypeCountId = hostelReservationRoomTypeCountId;
        this.roomId = roomId;
        this.clientId = clientId;
        this.actualPrice = actualPrice;
        this.actualDate = actualDate;
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public HostelReservationRoom() {
    }

    public static HostelReservationRoomBuilder builder() {
        return new HostelReservationRoomBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getRandomCodeInfoFill() {
        return this.randomCodeInfoFill;
    }

    public UUID getHostelReservationRoomTypeCountId() {
        return this.hostelReservationRoomTypeCountId;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public UUID getClientId() {
        return this.clientId;
    }

    public BigDecimal getActualPrice() {
        return this.actualPrice;
    }

    public LocalDateTime getActualDate() {
        return this.actualDate;
    }

    public LocalDateTime getCheckInDateTime() {
        return this.checkInDateTime;
    }

    public LocalDateTime getCheckOutDateTime() {
        return this.checkOutDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRandomCodeInfoFill(String randomCodeInfoFill) {
        this.randomCodeInfoFill = randomCodeInfoFill;
    }

    public void setHostelReservationRoomTypeCountId(UUID hostelReservationRoomTypeCountId) {
        this.hostelReservationRoomTypeCountId = hostelReservationRoomTypeCountId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public void setActualDate(LocalDateTime actualDate) {
        this.actualDate = actualDate;
    }

    public void setCheckInDateTime(LocalDateTime checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "HostelReservationRoom(id=" + this.getId() + ", randomCodeInfoFill=" + this.getRandomCodeInfoFill() + ", hostelReservationRoomTypeCountId=" + this.getHostelReservationRoomTypeCountId() + ", roomId=" + this.getRoomId() + ", clientId=" + this.getClientId() + ", actualPrice=" + this.getActualPrice() + ", actualDate=" + this.getActualDate() + ", checkInDateTime=" + this.getCheckInDateTime() + ", checkOutDateTime=" + this.getCheckOutDateTime() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class HostelReservationRoomBuilder {
        private UUID id;
        private String randomCodeInfoFill;
        private UUID hostelReservationRoomTypeCountId;
        private UUID roomId;
        private UUID clientId;
        private BigDecimal actualPrice;
        private LocalDateTime actualDate;
        private LocalDateTime checkInDateTime;
        private LocalDateTime checkOutDateTime;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        HostelReservationRoomBuilder() {
        }

        public HostelReservationRoomBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HostelReservationRoomBuilder randomCodeInfoFill(String randomCodeInfoFill) {
            this.randomCodeInfoFill = randomCodeInfoFill;
            return this;
        }

        public HostelReservationRoomBuilder hostelReservationRoomTypeCountId(UUID hostelReservationRoomTypeCountId) {
            this.hostelReservationRoomTypeCountId = hostelReservationRoomTypeCountId;
            return this;
        }

        public HostelReservationRoomBuilder roomId(UUID roomId) {
            this.roomId = roomId;
            return this;
        }

        public HostelReservationRoomBuilder clientId(UUID clientId) {
            this.clientId = clientId;
            return this;
        }

        public HostelReservationRoomBuilder actualPrice(BigDecimal actualPrice) {
            this.actualPrice = actualPrice;
            return this;
        }

        public HostelReservationRoomBuilder actualDate(LocalDateTime actualDate) {
            this.actualDate = actualDate;
            return this;
        }

        public HostelReservationRoomBuilder checkInDateTime(LocalDateTime checkInDateTime) {
            this.checkInDateTime = checkInDateTime;
            return this;
        }

        public HostelReservationRoomBuilder checkOutDateTime(LocalDateTime checkOutDateTime) {
            this.checkOutDateTime = checkOutDateTime;
            return this;
        }

        public HostelReservationRoomBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelReservationRoomBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public HostelReservationRoom build() {
            return new HostelReservationRoom(this.id, this.randomCodeInfoFill, this.hostelReservationRoomTypeCountId, this.roomId, this.clientId, this.actualPrice, this.actualDate, this.checkInDateTime, this.checkOutDateTime, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "HostelReservationRoom.HostelReservationRoomBuilder(id=" + this.id + ", randomCodeInfoFill=" + this.randomCodeInfoFill + ", hostelReservationRoomTypeCountId=" + this.hostelReservationRoomTypeCountId + ", roomId=" + this.roomId + ", clientId=" + this.clientId + ", actualPrice=" + this.actualPrice + ", actualDate=" + this.actualDate + ", checkInDateTime=" + this.checkInDateTime + ", checkOutDateTime=" + this.checkOutDateTime + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
