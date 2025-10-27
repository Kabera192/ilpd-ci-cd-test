package rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliberationDistinctionGroupRequest {
    @NotBlank(message = "Deliberation distinction group name is required")
    @RestrictedString
    private String name;
    private ValidityStatus status;
    private List<DeliberationDistinctionRequest> distinctions;
}

