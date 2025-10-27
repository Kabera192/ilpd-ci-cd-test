package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.reportingservice.model.nosql.document.AnnualToDo;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormType;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnualToDoRepository extends MongoRepository<AnnualToDo, String> {
    Optional<AnnualToDo> findByRequestId(String requestId);

    Optional<AnnualToDo> findByRequestIdAndIdNot(String requestId, String id);

    List<AnnualToDo> findByIsDeleted(boolean deleteStatus);

    List<AnnualToDo> findByIsDeletedOrderByCreatedAtDesc(boolean deleteStatus);

    Page<AnnualToDo> findByIsDeleted(boolean deleteStatus, Pageable pageable);

    Page<AnnualToDo> findByDescriptionContainingIgnoreCaseAndIsDeleted(String description, boolean isDeleted, Pageable pageable);

    Page<AnnualToDo> findByRequestIdContainingIgnoreCaseAndIsDeleted(String requestId, boolean isDeleted, Pageable pageable);

    Page<AnnualToDo> findByStatusAndIsDeleted(ToDoStatus status, boolean isDeleted, Pageable pageable);

    Page<AnnualToDo> findByUnitIdAndIsDeleted(UUID unitId, boolean isDeleted, Pageable pageable);

    List<AnnualToDo> findByStartDateBetweenAndIsDeleted(LocalDate startDate, LocalDate endDate, boolean deleteStatus);

    List<AnnualToDo> findByEndDateBeforeAndIsDeleted(LocalDate endDate, boolean deleteStatus);

    List<AnnualToDo> findByStatusInAndIsDeleted(List<ToDoStatus> statuses, boolean isDeleted);

    boolean existsByRequestId(String requestId);

    boolean existsByRequestIdAndIdNot(String requestId, String id);

    long countByIsDeleted(boolean deleteStatus);

    long countByStatusAndIsDeleted(ToDoStatus status, boolean isDeleted);

    Optional<AnnualToDo> findByIdAndIsDeleted(String id, boolean isDeleted);

    @Query("{ 'cost': { $gte: ?0, $lte: ?1 }, 'deleteStatus': ?2 }")
    Page<AnnualToDo> findByCostRangeAndIsDeleted(BigDecimal minCost, BigDecimal maxCost, boolean deleteStatus, Pageable pageable);

    Page<AnnualToDo> findByAssignedTo(String assignedTo, Pageable pageable);

    Page<AnnualToDo> findByPriority(Integer priority, Pageable pageable);

    @Repository
    interface EvaluationFormTypeRepository  extends MongoRepository<EvaluationFormType, String> {
        Optional<EvaluationFormType> findByName(String name);
        Page<EvaluationFormType>  findByIsDeletedFalseOrderByNameAsc(Pageable pageable);
        Optional<EvaluationFormType> findByNameAndIdNot(String name, String id);

        List<EvaluationFormType> findByIsDeletedFalse();

        List<EvaluationFormType> findByIsDeletedFalseOrderByNameAsc();

        Page<EvaluationFormType> findByIsDeletedFalse(Pageable pageable);

        Page<EvaluationFormType> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);

        boolean existsByName(String name);

        boolean existsByNameAndIdNot(String name, String id);

        long countByIsDeletedFalse();
    }
}
