package rw.ac.ilpd.reportingservice.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoRequest;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoResponse;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoStatusSummary;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
public interface AnnualToDoService {

    AnnualToDoResponse create(AnnualToDoRequest request);

    AnnualToDoResponse getById(String id);

    AnnualToDoResponse getByRequestId(String requestId);

    AnnualToDoResponse update(String id, AnnualToDoRequest request);

    AnnualToDoResponse updateStatus(String id, String newStatus);

    void delete(String id);

    void softDelete(String id);

    void restore(String id);

    Page<AnnualToDoResponse> getAll(Pageable pageable);

    Page<AnnualToDoResponse> getAllActive(Pageable pageable);

    Page<AnnualToDoResponse> getByAssignedTo(String assignedTo, Pageable pageable);

    Page<AnnualToDoResponse> searchByDescription(String description, Pageable pageable);

    List<AnnualToDoResponse> getByDateRange(LocalDate startDate, LocalDate endDate);

    Page<AnnualToDoResponse> getByPriority(Integer priority, Pageable pageable);

    List<AnnualToDoResponse> getAllActiveList();

    List<AnnualToDoStatusSummary> getStatusSummaryByUnitId(UUID unitId);

    boolean existsById(String id);

    boolean existsByRequestId(String requestId);

    long countActive();


    Page<AnnualToDoResponse> getByStatus(String status, Pageable pageable);

    Page<AnnualToDoResponse> getByUnitId(String unitId, Pageable pageable);

    Page<AnnualToDoResponse> searchByRequestId(@NotBlank(message = "Search request ID cannot be blank") String requestId, Pageable pageable);

    Page<AnnualToDoResponse> getByCostRange(@NotNull(message = "Min cost is required") @DecimalMin(value = "0.0", inclusive = false, message = "Min cost must be positive") BigDecimal minCost, @NotNull(message = "Max cost is required") @DecimalMin(value = "0.0", inclusive = false, message = "Max cost must be positive") BigDecimal maxCost, Pageable pageable);

    List<AnnualToDoResponse> getOverdueTasks();

    long countByStatus(String status);

    List<AnnualToDoResponse> getByMultipleStatuses(@NotEmpty(message = "Statuses list cannot be empty") List<String> statuses);
}
