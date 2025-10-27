package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.nosql.document.AuctionItem;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemResponse;

import java.util.UUID;

@Component
public class AuctionItemMapper {
    public AuctionItem toAuctionItem(AuctionItemRequest request, UUID recordedBy) {
        return AuctionItem.builder()
                .stockOutId(UUID.fromString(request.getStockOutId()))
                .recordedBy(recordedBy)
                .quantity(request.getQuantity().doubleValue())
                .build();
    }

    public AuctionItemResponse fromAuctionItem(AuctionItem auctionItem) {
        return AuctionItemResponse.builder()
                .id(auctionItem.getId())
                .stockOutId(auctionItem.getStockOutId() != null ? auctionItem.getStockOutId().toString() : null)
                .recordedBy(auctionItem.getRecordedBy() != null ? auctionItem.getRecordedBy().toString() : null)
                .quantity(auctionItem.getQuantity() != null ? auctionItem.getQuantity().intValue() : null)
                .createdAt(auctionItem.getCreatedAt() != null ? auctionItem.getCreatedAt().toString() : null)
                .build();
    }
}