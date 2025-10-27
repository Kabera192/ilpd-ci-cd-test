package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.InstitutionShortCourseSponsor;

import java.util.UUID;

public interface InstitutionShortCourseSponsorRepository extends JpaRepository<InstitutionShortCourseSponsor, UUID> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
