package rw.ac.ilpd.mis.shared.dto.request;

import lombok.*;
import rw.ac.ilpd.mis.shared.enums.RequestPartyType;

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