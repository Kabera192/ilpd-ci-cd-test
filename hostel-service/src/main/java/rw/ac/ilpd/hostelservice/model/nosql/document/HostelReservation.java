package rw.ac.ilpd.hostelservice.model.nosql.document;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;
import rw.ac.ilpd.sharedlibrary.enums.ReservationStatus;
import rw.ac.ilpd.sharedlibrary.enums.ReservationType;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "hostel_hostel_reservations")
public class HostelReservation {
    @Id
    private String id;
    /*reserver_person_id is referencing to external user*/
    private String reserverPersonId;
    private int numberOfPeople;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private int monthsToSpend;
    private int numberOfRooms;
    private String randomQueryCode;
    private ReservationType reservationType;
    private ReservationStatus reservationStatus;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    List<HostelReservationRoomTypeCount> hostelReservationRoomTypeCounts;

    public HostelReservation(String id, String reserverPersonId, int numberOfPeople, LocalDateTime arrivalDate, LocalDateTime departureDate, int monthsToSpend, int numberOfRooms, String randomQueryCode, ReservationType reservationType, ReservationStatus reservationStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<HostelReservationRoomTypeCount> hostelReservationRoomTypeCounts) {
        this.id = id;
        this.reserverPersonId = reserverPersonId;
        this.numberOfPeople = numberOfPeople;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.monthsToSpend = monthsToSpend;
        this.numberOfRooms = numberOfRooms;
        this.randomQueryCode = randomQueryCode;
        this.reservationType = reservationType;
        this.reservationStatus = reservationStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hostelReservationRoomTypeCounts = hostelReservationRoomTypeCounts;
    }

    public HostelReservation() {
    }

    public static HostelReservationBuilder builder() {
        return new HostelReservationBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getReserverPersonId() {
        return this.reserverPersonId;
    }

    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public LocalDateTime getArrivalDate() {
        return this.arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return this.departureDate;
    }

    public int getMonthsToSpend() {
        return this.monthsToSpend;
    }

    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    public String getRandomQueryCode() {
        return this.randomQueryCode;
    }

    public ReservationType getReservationType() {
        return this.reservationType;
    }

    public ReservationStatus getReservationStatus() {
        return this.reservationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public List<HostelReservationRoomTypeCount> getHostelReservationRoomTypeCounts() {
        return this.hostelReservationRoomTypeCounts;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReserverPersonId(String reserverPersonId) {
        this.reserverPersonId = reserverPersonId;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public void setMonthsToSpend(int monthsToSpend) {
        this.monthsToSpend = monthsToSpend;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setRandomQueryCode(String randomQueryCode) {
        this.randomQueryCode = randomQueryCode;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setHostelReservationRoomTypeCounts(List<HostelReservationRoomTypeCount> hostelReservationRoomTypeCounts) {
        this.hostelReservationRoomTypeCounts = hostelReservationRoomTypeCounts;
    }

    public String toString() {
        return "HostelReservation(id=" + this.getId() + ", reserverPersonId=" + this.getReserverPersonId() + ", numberOfPeople=" + this.getNumberOfPeople() + ", arrivalDate=" + this.getArrivalDate() + ", departureDate=" + this.getDepartureDate() + ", monthsToSpend=" + this.getMonthsToSpend() + ", numberOfRooms=" + this.getNumberOfRooms() + ", randomQueryCode=" + this.getRandomQueryCode() + ", reservationType=" + this.getReservationType() + ", reservationStatus=" + this.getReservationStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", hostelReservationRoomTypeCounts=" + this.getHostelReservationRoomTypeCounts() + ")";
    }

    public static class HostelReservationBuilder {
        private String id;
        private String reserverPersonId;
        private int numberOfPeople;
        private LocalDateTime arrivalDate;
        private LocalDateTime departureDate;
        private int monthsToSpend;
        private int numberOfRooms;
        private String randomQueryCode;
        private ReservationType reservationType;
        private ReservationStatus reservationStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<HostelReservationRoomTypeCount> hostelReservationRoomTypeCounts;

        HostelReservationBuilder() {
        }

        public HostelReservationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public HostelReservationBuilder reserverPersonId(String reserverPersonId) {
            this.reserverPersonId = reserverPersonId;
            return this;
        }

        public HostelReservationBuilder numberOfPeople(int numberOfPeople) {
            this.numberOfPeople = numberOfPeople;
            return this;
        }

        public HostelReservationBuilder arrivalDate(LocalDateTime arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public HostelReservationBuilder departureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public HostelReservationBuilder monthsToSpend(int monthsToSpend) {
            this.monthsToSpend = monthsToSpend;
            return this;
        }

        public HostelReservationBuilder numberOfRooms(int numberOfRooms) {
            this.numberOfRooms = numberOfRooms;
            return this;
        }

        public HostelReservationBuilder randomQueryCode(String randomQueryCode) {
            this.randomQueryCode = randomQueryCode;
            return this;
        }

        public HostelReservationBuilder reservationType(ReservationType reservationType) {
            this.reservationType = reservationType;
            return this;
        }

        public HostelReservationBuilder reservationStatus(ReservationStatus reservationStatus) {
            this.reservationStatus = reservationStatus;
            return this;
        }

        public HostelReservationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelReservationBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public HostelReservationBuilder hostelReservationRoomTypeCounts(List<HostelReservationRoomTypeCount> hostelReservationRoomTypeCounts) {
            this.hostelReservationRoomTypeCounts = hostelReservationRoomTypeCounts;
            return this;
        }

        public HostelReservation build() {
            return new HostelReservation(this.id, this.reserverPersonId, this.numberOfPeople, this.arrivalDate, this.departureDate, this.monthsToSpend, this.numberOfRooms, this.randomQueryCode, this.reservationType, this.reservationStatus, this.createdAt, this.updatedAt, this.hostelReservationRoomTypeCounts);
        }
        @Override
        public String toString() {
            return "HostelReservation.HostelReservationBuilder(id=" + this.id + ", reserverPersonId=" + this.reserverPersonId + ", numberOfPeople=" + this.numberOfPeople + ", arrivalDate=" + this.arrivalDate + ", departureDate=" + this.departureDate + ", monthsToSpend=" + this.monthsToSpend + ", numberOfRooms=" + this.numberOfRooms + ", randomQueryCode=" + this.randomQueryCode + ", reservationType=" + this.reservationType + ", reservationStatus=" + this.reservationStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", hostelReservationRoomTypeCounts=" + this.hostelReservationRoomTypeCounts + ")";
        }
    }
}
