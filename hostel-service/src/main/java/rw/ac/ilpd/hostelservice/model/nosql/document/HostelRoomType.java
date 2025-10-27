package rw.ac.ilpd.hostelservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomImage;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomTypePricing;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "hostel_hostel_room_types")
public class HostelRoomType {
    @Id
    private String id;
    /* room can be Standard, VIP_SMALL, VIP_LARGE VVIP_SMALL, VVIP_LARGE*/
    private String type;
    //    private  int capacity;
    private int maxCapacity;
    //    private BigDecimal  price;
//    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    private List<HostelRoomImage> hostelRoomImages;
    private List<HostelRoomTypePricing> hostelRoomPrices;

    public HostelRoomType(String id, String type, int maxCapacity, LocalDateTime createdAt, List<HostelRoomImage> hostelRoomImages, List<HostelRoomTypePricing> hostelRoomPrices) {
        this.id = id;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.createdAt = createdAt;
        this.hostelRoomImages = hostelRoomImages;
        this.hostelRoomPrices = hostelRoomPrices;
    }

    public HostelRoomType() {
    }

    public static HostelRoomTypeBuilder builder() {
        return new HostelRoomTypeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public List<HostelRoomImage> getHostelRoomImages() {
        return this.hostelRoomImages;
    }

    public List<HostelRoomTypePricing> getHostelRoomPrices() {
        return this.hostelRoomPrices;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setHostelRoomImages(List<HostelRoomImage> hostelRoomImages) {
        this.hostelRoomImages = hostelRoomImages;
    }

    public void setHostelRoomPrices(List<HostelRoomTypePricing> hostelRoomPrices) {
        this.hostelRoomPrices = hostelRoomPrices;
    }

    public String toString() {
        return "HostelRoomType(id=" + this.getId() + ", type=" + this.getType() + ", maxCapacity=" + this.getMaxCapacity() + ", createdAt=" + this.getCreatedAt() + ", hostelRoomImages=" + this.getHostelRoomImages() + ", hostelRoomPrices=" + this.getHostelRoomPrices() + ")";
    }

    public static class HostelRoomTypeBuilder {
        private String id;
        private String type;
        private int maxCapacity;
        private LocalDateTime createdAt;
        private List<HostelRoomImage> hostelRoomImages;
        private List<HostelRoomTypePricing> hostelRoomPrices;

        HostelRoomTypeBuilder() {
        }

        public HostelRoomTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public HostelRoomTypeBuilder type(String type) {
            this.type = type;
            return this;
        }

        public HostelRoomTypeBuilder maxCapacity(int maxCapacity) {
            this.maxCapacity = maxCapacity;
            return this;
        }

        public HostelRoomTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelRoomTypeBuilder hostelRoomImages(List<HostelRoomImage> hostelRoomImages) {
            this.hostelRoomImages = hostelRoomImages;
            return this;
        }

        public HostelRoomTypeBuilder hostelRoomPrices(List<HostelRoomTypePricing> hostelRoomPrices) {
            this.hostelRoomPrices = hostelRoomPrices;
            return this;
        }

        public HostelRoomType build() {
            return new HostelRoomType(this.id, this.type, this.maxCapacity, this.createdAt, this.hostelRoomImages, this.hostelRoomPrices);
        }

        public String toString() {
            return "HostelRoomType.HostelRoomTypeBuilder(id=" + this.id + ", type=" + this.type + ", maxCapacity=" + this.maxCapacity + ", createdAt=" + this.createdAt + ", hostelRoomImages=" + this.hostelRoomImages + ", hostelRoomPrices=" + this.hostelRoomPrices + ")";
        }
    }
}
