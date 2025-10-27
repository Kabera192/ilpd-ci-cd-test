package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.mapper.StockOutMapper;
import rw.ac.ilpd.inventoryservice.model.sql.*;
import rw.ac.ilpd.inventoryservice.repository.sql.ItemRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.StockInRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.StockOutRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockOutService {
    private final StockOutRepository stockOutRepository;
    private final StockOutMapper stockOutMapper;
    private final StockInService stockInService;
    private final RoomService roomService;
    private final UserItemRequisitionService userItemRequisitionService;
    private final StockInRepository stockInRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public PagedResponse<StockOutResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if(order.equals("desc"))
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        else
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<StockOut> stockOutPage = stockOutRepository.findAll(pageable);
        return new PagedResponse<>(
                stockOutPage.getContent().stream().map(stockOutMapper::fromStockOut).toList(),
                stockOutPage.getNumber(),
                stockOutPage.getSize(),
                stockOutPage.getTotalElements(),
                stockOutPage.getTotalPages(),
                stockOutPage.isLast()
        );
    }

    public StockOutResponse get(UUID id) {
        return stockOutMapper.fromStockOut(getEntity(id).orElseThrow(() ->
                new EntityNotFoundException("StockOut not found")));
    }

    public StockOutResponse create(StockOutRequest request) {
        // Fetch related entities
        StockIn stockIn = stockInService.getEntity(UUID.fromString(request.getStockInId()))
                .orElseThrow(() -> new EntityNotFoundException("StockIn not found"));
        Room room = roomService.getEntity(UUID.fromString(request.getRoomId()))
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        UserItemRequisition userItemRequisition = null;
        if (request.getUserItemRequisitionId() != null && !request.getUserItemRequisitionId().isEmpty()) {
            userItemRequisition = userItemRequisitionService.getEntity(UUID.fromString(request.getUserItemRequisitionId()))
                    .orElseThrow(() -> new EntityNotFoundException("UserItemRequisition not found"));
        }

        // adjust quantity from stock in
        stockIn.setRemainingQuantity(stockIn.getRemainingQuantity() - request.getQuantity());
        stockInRepository.save(stockIn);

        // adjust qty from item
        Item item = itemService.getEntity(stockIn.getItemId().getId()).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setRemainingQuantity(item.getRemainingQuantity() - request.getQuantity());
        itemRepository.save(item);

        StockOut stockOut = stockOutMapper.toStockOut(request, stockIn, room, userItemRequisition);
        return stockOutMapper.fromStockOut(stockOutRepository.save(stockOut));
    }

    public Boolean delete(UUID id) {
        StockOut stockOut = getEntity(id).orElseThrow(() ->
                new EntityNotFoundException("StockOut not found"));
        // add the quantity back to the stock in and item
        StockIn stockIn = stockInService.getEntity(stockOut.getStockIn().getId())
                .orElseThrow(() -> new EntityNotFoundException("StockIn not found"));

        // adjust quantity from stock in
        stockIn.setRemainingQuantity(stockIn.getRemainingQuantity() + stockOut.getQuantity());
        stockInRepository.save(stockIn);

        // adjust qty from item
        Item item = itemService.getEntity(stockIn.getItemId().getId()).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        item.setRemainingQuantity(item.getRemainingQuantity() + stockOut.getQuantity());
        itemRepository.save(item);

        stockOutRepository.delete(stockOut);
        return true;
    }

    public Optional<StockOut> getEntity(UUID id) {
        return stockOutRepository.findById(id);
    }
}