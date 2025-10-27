package rw.ac.ilpd.sharedlibrary.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.RequestPartyType;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTypeRoleRequest {

    @NotNull(message = "Role ID is required")
    private UUID roleId;

    @NotNull(message = "Request Party type is required")
    private RequestPartyType party;

    @NotNull(message = "Priority is required")
    private Integer priority;
}