package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.SourceOfFund;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundResponse;

@Component
public class SourceOfFundMapper {
    public SourceOfFund toSourceOfFund(SourceOfFundRequest request) {
        return SourceOfFund.builder()
                .name(request.getName())
                .description(request.getDescription())
                .phone(request.getPhone())
                .email(request.getEmail())
                .isDeleted(false) // New records are not deleted by default
                .build();
    }

    public SourceOfFundResponse fromSourceOfFund(SourceOfFund sourceOfFund) {
        return SourceOfFundResponse.builder()
                .id(sourceOfFund.getId() != null ? sourceOfFund.getId().toString() : null)
                .name(sourceOfFund.getName())
                .description(sourceOfFund.getDescription())
                .phone(sourceOfFund.getPhone())
                .email(sourceOfFund.getEmail())
                .isDeleted(sourceOfFund.isDeleted())
                .createdAt(sourceOfFund.getCreatedAt() != null ? sourceOfFund.getCreatedAt().toString() : null)
                .build();
    }
}