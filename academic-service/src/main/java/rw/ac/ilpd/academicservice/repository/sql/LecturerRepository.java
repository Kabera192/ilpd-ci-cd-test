package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LecturerRepository extends JpaRepository<Lecturer, UUID>
{
    Optional<Lecturer> findByUserIdAndActiveStatus(UUID userId, EmploymentStatus activeStatus);

    Page<Lecturer> findByUserIdIn(List<UUID> userIds, Pageable pageable);

    Optional<Lecturer> findFirstByUserIdOrderByCreatedAtDesc(UUID uuid);

    Optional<Lecturer> findByIdAndUserIdOrderByCreatedAtDesc(UUID lecturerId, UUID userId);

    List<Lecturer> findAllByIdIn(List<UUID> lecturerIds);
}
