package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.StockInMapper;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;
import rw.ac.ilpd.inventoryservice.model.sql.DeliveryNote;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.repository.sql.ItemRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.StockInRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockInService {
    private final StockInRepository stockInRepository;
    private final StockInMapper stockInMapper;
    private final ItemService itemService;
    private final DeliveryNoteService deliveryNoteService;
    private final ItemRepository itemRepository;

    public PagedResponse<StockInResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<StockIn> stockInPage = stockInRepository.findAll(pageable);

        return new PagedResponse<>(
                stockInPage.getContent().stream().map(stockInMapper::fromStockIn).toList(),
                stockInPage.getNumber(),
                stockInPage.getSize(),
                stockInPage.getTotalElements(),
                stockInPage.getTotalPages(),
                stockInPage.isLast()
        );
    }

    public StockInResponse get(UUID id) {
        return stockInMapper.fromStockIn(getEntity(id).orElseThrow(
                () -> new EntityNotFoundException("StockIn not found with id: " + id)));
    }

    public StockInResponse create(StockInRequest request) {
        Item item = itemService.getEntity(UUID.fromString(request.getItemId()))
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + request.getItemId()));

        DeliveryNote deliveryNote = deliveryNoteService.getEntity(UUID.fromString(request.getDeliveryNoteId()))
                .orElseThrow(() -> new EntityNotFoundException("DeliveryNote not found with id: " + request.getDeliveryNoteId()));

        // add the quantity
        item.setRemainingQuantity(item.getRemainingQuantity() + request.getQuantity());
        itemRepository.save(item);


        StockIn stockIn = stockInMapper.toStockIn(request, item, deliveryNote);
        return stockInMapper.fromStockIn(stockInRepository.save(stockIn));
    }


    public Boolean delete(UUID id) {
        StockIn stockIn = getEntity(id).orElseThrow(
                () -> new EntityNotFoundException("StockIn not found with id: " + id));
        // Remove the quantity
        Item item = itemService.getEntity(stockIn.getItemId().getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setRemainingQuantity(item.getRemainingQuantity() - stockIn.getRemainingQuantity());
        itemRepository.save(item);

        stockInRepository.delete(stockIn);
        return true;
    }

    public Optional<StockIn> getEntity(UUID id) {
        return stockInRepository.findById(id);
    }
}