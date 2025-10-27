package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSearchRequest {
    @RestrictedString
    private String userId;
    @RestrictedString
    private String intakeId;
    @RestrictedString
    private String status;
    @RestrictedString
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
