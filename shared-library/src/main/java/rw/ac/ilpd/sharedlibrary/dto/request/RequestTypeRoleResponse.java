package rw.ac.ilpd.sharedlibrary.dto.request;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.RequestPartyType;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTypeRoleResponse
{
    private UUID roleId;
    private RequestPartyType party;
    private Integer priority;
}