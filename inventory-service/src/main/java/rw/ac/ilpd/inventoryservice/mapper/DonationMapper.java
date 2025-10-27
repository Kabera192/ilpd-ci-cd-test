package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.Donation;
import rw.ac.ilpd.inventoryservice.model.sql.SourceOfFund;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationResponse;

@Component
public class DonationMapper {
    public Donation toDonation(DonationRequest request, StockIn stockIn, SourceOfFund donor) {
        return Donation.builder()
                .stockInId(stockIn)
                .donorId(donor)
                .quantity(request.getQuantity().doubleValue())
                .build();
    }

    public DonationResponse fromDonation(Donation donation) {
        return DonationResponse.builder()
                .id(donation.getId() != null ? donation.getId().toString() : null)
                .stockInId(donation.getStockInId() != null ? donation.getStockInId().getId().toString() : null)
                .donorId(donation.getDonorId() != null ? donation.getDonorId().getId().toString() : null)
                .quantity(donation.getQuantity() != null ? donation.getQuantity().intValue() : null)
                .build();
    }
}