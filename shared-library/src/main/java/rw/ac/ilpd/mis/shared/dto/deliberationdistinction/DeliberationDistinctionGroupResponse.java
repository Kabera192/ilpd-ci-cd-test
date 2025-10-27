package rw.ac.ilpd.mis.shared.dto.deliberationdistinction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.enums.ValidityStatus;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeliberationDistinctionGroupResponse {
    private String id;
    private String name;
    private ValidityStatus status;
    private List<DeliberationDistinctionRequest> distinctions;
}
