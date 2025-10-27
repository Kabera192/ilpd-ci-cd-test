package rw.ac.ilpd.mis.shared.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchResponse<T> {
    private List<T> successful;
    private List<BatchError> errors;
    private int totalProcessed;
    private int successCount;
    private int errorCount;
}

