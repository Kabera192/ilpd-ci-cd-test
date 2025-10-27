package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSearchRequest {
    private String userId;
    private String intakeId;
    private String status;
    private String nextOfKinId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // Pagination
    @Min(value = 0, message = "Page must be non-negative")
    @Builder.Default
    private int page = 0;

    @Min(value = 1, message = "Size must be at least 1")
    @Max(value = 100, message = "Size must not exceed 100")
    @Builder.Default
    private int size = 20;

    // Sorting
    @Builder.Default
    private String sortBy = "createdAt";
    @Builder.Default
    private String sortDirection = "DESC";
}
