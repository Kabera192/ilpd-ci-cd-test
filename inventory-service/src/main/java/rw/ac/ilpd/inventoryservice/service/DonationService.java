package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.mapper.DonationMapper;
import rw.ac.ilpd.inventoryservice.model.sql.Donation;
import rw.ac.ilpd.inventoryservice.model.sql.SourceOfFund;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;
import rw.ac.ilpd.inventoryservice.repository.sql.DonationRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final StockInService stockInService;
    private final SourceOfFundService sourceOfFundService;

    public PagedResponse<DonationResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<Donation> donationPage = donationRepository.findAll(pageable);
        return new PagedResponse<>(
                donationPage.getContent().stream().map(donationMapper::fromDonation).toList(),
                donationPage.getNumber(),
                donationPage.getSize(),
                donationPage.getTotalElements(),
                donationPage.getTotalPages(),
                donationPage.isLast()
        );
    }

    public DonationResponse get(UUID id) {
        return donationMapper.fromDonation(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("Donation not found"))
        );
    }

    public DonationResponse create(DonationRequest request) {
        // Get related entities
        StockIn stockIn = stockInService.getEntity(UUID.fromString(request.getStockInId()))
                .orElseThrow(() -> new EntityNotFoundException("StockIn not found with id: " + request.getStockInId()));

        SourceOfFund donor = sourceOfFundService.getEntity(UUID.fromString(request.getDonorId()))
                .orElseThrow(() -> new EntityNotFoundException("SourceOfFund not found with id: " + request.getDonorId()));

        Donation donation = donationMapper.toDonation(request, stockIn, donor);
        return donationMapper.fromDonation(donationRepository.save(donation));
    }

    public DonationResponse edit(UUID id, DonationRequest request) {
        Donation existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation not found"));

        // Get related entities
        StockIn stockIn = stockInService.getEntity(UUID.fromString(request.getStockInId()))
                .orElseThrow(() -> new EntityNotFoundException("StockIn not found with id: " + request.getStockInId()));

        SourceOfFund donor = sourceOfFundService.getEntity(UUID.fromString(request.getDonorId()))
                .orElseThrow(() -> new EntityNotFoundException("SourceOfFund not found with id: " + request.getDonorId()));

        existing.setStockInId(stockIn);
        existing.setDonorId(donor);
        existing.setQuantity(request.getQuantity().doubleValue());

        return donationMapper.fromDonation(donationRepository.save(existing));
    }

    public DonationResponse patch(UUID id, Map<String, Object> updates) {
        Donation existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation not found"));

        // Patch stockInId
        if (updates.containsKey("stockInId")) {
            Object value = updates.get("stockInId");
            if (value instanceof String stockInIdStr) {
                try {
                    UUID stockInId = UUID.fromString(stockInIdStr);
                    StockIn stockIn = stockInService.getEntity(stockInId)
                            .orElseThrow(() -> new EntityNotFoundException("StockIn not found with id: " + stockInIdStr));
                    existing.setStockInId(stockIn);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for stockInId");
                }
            }
        }

        // Patch donorId
        if (updates.containsKey("donorId")) {
            Object value = updates.get("donorId");
            if (value instanceof String donorIdStr) {
                try {
                    UUID donorId = UUID.fromString(donorIdStr);
                    SourceOfFund donor = sourceOfFundService.getEntity(donorId)
                            .orElseThrow(() -> new EntityNotFoundException("SourceOfFund not found with id: " + donorIdStr));
                    existing.setDonorId(donor);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for donorId");
                }
            }
        }

        // Patch quantity
        if (updates.containsKey("quantity")) {
            Object value = updates.get("quantity");
            if (value instanceof Integer quantity && quantity > 0) {
                existing.setQuantity(quantity.doubleValue());
            } else {
                throw new IllegalArgumentException("Quantity must be a positive integer");
            }
        }

        return donationMapper.fromDonation(donationRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        Donation existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation not found"));
        donationRepository.delete(existing);
        return true;
    }

    public Optional<Donation> getEntity(UUID id) {
        return donationRepository.findById(id);
    }
}