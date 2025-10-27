package rw.ac.ilpd.mis.shared.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserItemRequisitionRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Request ID is required")
    private String requestId;

    @NotBlank(message = "Item ID is required")
    private String itemId;

    @NotNull(message = "Proposed quantity is required")
    @Positive(message = "Proposed quantity must be positive")
    private Integer proposedQuantity;

    
}