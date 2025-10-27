package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.Room;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;
import rw.ac.ilpd.inventoryservice.model.sql.StockOut;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class StockOutMapper {
    public StockOut toStockOut(StockOutRequest request, StockIn stockIn, Room room, UserItemRequisition userItemRequisition) {
        return StockOut.builder()
                .stockIn(stockIn)
                .roomId(room)
                .userItemRequisition(userItemRequisition)
                .quantity(request.getQuantity())
                .build();
    }

    public StockOutResponse fromStockOut(StockOut stockOut) {
        return StockOutResponse.builder()
                .id(stockOut.getId() != null ? stockOut.getId().toString() : null)
                .stockInId(stockOut.getStockIn() != null ? stockOut.getStockIn().getId().toString() : null)
                .roomId(stockOut.getRoomId() != null ? stockOut.getRoomId().getId().toString() : null)
                .userItemRequisitionId(stockOut.getUserItemRequisition() != null ?
                        stockOut.getUserItemRequisition().getId().toString() : null)
                .quantity(stockOut.getQuantity())
                .createdAt(stockOut.getCreatedAt() != null ? stockOut.getCreatedAt().toString() : null)
                .build();
    }
}