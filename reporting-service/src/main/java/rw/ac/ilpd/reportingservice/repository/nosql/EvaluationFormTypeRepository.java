package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormType;

import java.util.List;
import java.util.Optional;
@Repository
public interface EvaluationFormTypeRepository  extends MongoRepository<EvaluationFormType, String> {
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

