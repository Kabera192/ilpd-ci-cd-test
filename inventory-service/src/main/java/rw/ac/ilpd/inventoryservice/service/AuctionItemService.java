package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.AuctionItemMapper;
import rw.ac.ilpd.inventoryservice.model.nosql.document.AuctionItem;
import rw.ac.ilpd.inventoryservice.repository.nosql.AuctionItemRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.ItemRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.StockInRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.StockOutRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuctionItemService {
    private final AuctionItemRepository auctionItemRepository;
    private final AuctionItemMapper auctionItemMapper;
    private final StockOutService stockOutService; // Assuming you have this service for stockOutId validation
    private final StockOutRepository stockOutRepository;
    private final StockInRepository stockInRepository;
    private final ItemRepository itemRepository;

    public PagedResponse<AuctionItemResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<AuctionItem> auctionItemPage = auctionItemRepository.findAll(pageable);
        return new PagedResponse<>(
                auctionItemPage.getContent().stream().map(auctionItemMapper::fromAuctionItem).toList(),
                auctionItemPage.getNumber(),
                auctionItemPage.getSize(),
                auctionItemPage.getTotalElements(),
                auctionItemPage.getTotalPages(),
                auctionItemPage.isLast()
        );
    }

    public AuctionItemResponse get(String id) {
        return auctionItemMapper.fromAuctionItem(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Auction item not found")));
    }

    public AuctionItemResponse create(AuctionItemRequest request, UUID recordedBy) {
        // Validate stockOut exists
         stockOutService.getEntity(UUID.fromString(request.getStockOutId()))
                .orElseThrow(() -> new EntityNotFoundException("Stock out not found with id: " + request.getStockOutId()));

        AuctionItem auctionItem = auctionItemMapper.toAuctionItem(request, recordedBy);
        // add the quantity to stock out


        return auctionItemMapper.fromAuctionItem(auctionItemRepository.save(auctionItem));
    }

    public Boolean delete(String id) {
        AuctionItem existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Auction item not found"));
        auctionItemRepository.delete(existing);
        return true;
    }

    public Optional<AuctionItem> getEntity(String id) {
        return auctionItemRepository.findById(id);
    }
}