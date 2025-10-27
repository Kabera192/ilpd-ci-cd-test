package rw.ac.ilpd.mis.shared.dto.deliberationdistinction;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.enums.ValidityStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliberationDistinctionGroupRequest {
    @NotBlank(message = "Deliberation distinction group name is required")
    private String name;
    private ValidityStatus status;
    private List<DeliberationDistinctionRequest> distinctions;
}

