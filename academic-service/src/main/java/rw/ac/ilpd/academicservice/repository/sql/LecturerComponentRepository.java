package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Component;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LecturerComponentRepository extends JpaRepository<LecturerComponent, UUID> {
    boolean existsByLecturerAndComponent(Lecturer lecturer, Component component);
    Page<LecturerComponent> findByComponentId(UUID uuid, Pageable pageable);
    List<LecturerComponent> findAllByLecturer_IdAndLecturer_ActiveStatus(UUID id, EmploymentStatus status);
}
