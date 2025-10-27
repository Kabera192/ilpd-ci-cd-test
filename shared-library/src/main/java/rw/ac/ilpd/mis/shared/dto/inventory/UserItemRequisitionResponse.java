package rw.ac.ilpd.mis.shared.dto.inventory;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserItemRequisitionResponse {
    private String id;
    private String userId;
    private String requestId;
    private String itemId;
    private Integer proposedQuantity;
    private Boolean deleteStatus;
}