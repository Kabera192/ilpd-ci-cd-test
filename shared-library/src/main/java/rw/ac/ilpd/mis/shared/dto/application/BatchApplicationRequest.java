package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchApplicationRequest {
    @NotEmpty(message = "Applications list cannot be empty")
    @Valid
    private List<ApplicationRequest> applications;
}