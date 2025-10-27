package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.HeadOfModule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HeadOfModuleRepository extends JpaRepository<HeadOfModule, UUID> {

    List<HeadOfModule> findByToIsNotNull();

    List<HeadOfModule> findByToIsNull();

    boolean existsByLecturerIdAndModuleIdAndToIsNull(UUID uuid, UUID uuid1);

    boolean existsByModuleIdAndToIsNull(UUID uuid);

    List<HeadOfModule> findByToIsNullOrToAfter(LocalDateTime now);

    List<HeadOfModule> findByToBetweenAndToNotNull(LocalDateTime from, LocalDateTime to);
//    get active paged head of module
    Page<HeadOfModule> findAllByToIsNullOrToAfter(LocalDateTime now, Pageable pageable);
}

