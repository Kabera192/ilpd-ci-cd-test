package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Component;

import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository extends JpaRepository<Component, UUID>
{
    Optional<Component> findByNameOrAcronym(String name, String acronym);
}
