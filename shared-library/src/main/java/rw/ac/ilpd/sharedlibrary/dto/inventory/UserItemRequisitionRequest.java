package rw.ac.ilpd.sharedlibrary.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserItemRequisitionRequest {

    @NotBlank(message = "User ID is required")
    @RestrictedString
    private String userId;

    @NotBlank(message = "Request ID is required")
    @RestrictedString
    private String requestId;

    @NotBlank(message = "Item ID is required")
    @RestrictedString
    private String itemId;

    @NotNull(message = "Proposed quantity is required")
    @Positive(message = "Proposed quantity must be positive")
    private Integer proposedQuantity;

    
}