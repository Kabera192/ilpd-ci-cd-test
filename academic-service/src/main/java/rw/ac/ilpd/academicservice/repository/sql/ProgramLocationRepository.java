package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.ProgramLocation;

import java.util.UUID;

public interface ProgramLocationRepository extends JpaRepository<ProgramLocation, UUID>
{
    boolean existsByProgramAndLocationId(Program program, String locationId);

    Page<Program> findProgramByLocationId(String locationId, Pageable pageable);
}
