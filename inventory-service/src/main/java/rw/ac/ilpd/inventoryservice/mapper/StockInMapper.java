package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInResponse;
import rw.ac.ilpd.inventoryservice.model.sql.DeliveryNote;
import rw.ac.ilpd.inventoryservice.model.sql.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class StockInMapper {
    public StockIn toStockIn(StockInRequest request, Item item, DeliveryNote deliveryNote) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return StockIn.builder()
                .itemId(item)
                .unitPrice(request.getUnitPrice().doubleValue())
                .quantity(request.getQuantity())
                .acquisitionDate(request.getAcquisitionDate() != null ?
                        LocalDate.parse(request.getAcquisitionDate(), dateFormatter) : null)
                .expirationDate(request.getExpirationDate() != null ?
                        LocalDate.parse(request.getExpirationDate(), dateFormatter) : null)
                .deliveryNote(deliveryNote)
                .remainingQuantity(request.getQuantity())
                .build();
    }

    public StockInResponse fromStockIn(StockIn stockIn) {
        return StockInResponse.builder()
                .id(stockIn.getId() != null ? stockIn.getId().toString() : null)
                .itemId(stockIn.getItemId() != null ? stockIn.getItemId().getId().toString() : null)
                .unitPrice(BigDecimal.valueOf(stockIn.getUnitPrice()))
                .quantity(stockIn.getQuantity())
                .acquisitionDate(stockIn.getAcquisitionDate() != null ?
                        stockIn.getAcquisitionDate().toString() : null)
                .expirationDate(stockIn.getExpirationDate() != null ?
                        stockIn.getExpirationDate().toString() : null)
                .deliveryNoteId(stockIn.getDeliveryNote() != null ?
                        stockIn.getDeliveryNote().getId().toString() : null)
                .remainingQuantity(stockIn.getRemainingQuantity())
                .createdAt(stockIn.getCreatedAt() != null ?
                        stockIn.getCreatedAt().toString() : null)
                .build();
    }
}