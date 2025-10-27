package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponentMaterial;

import java.util.UUID;

public interface LecturerComponentMaterialRepository
        extends JpaRepository<LecturerComponentMaterial, UUID>
{
    Page<LecturerComponentMaterial> findByLecturerComponent(LecturerComponent lecturerComponent, Pageable pageable);
}
