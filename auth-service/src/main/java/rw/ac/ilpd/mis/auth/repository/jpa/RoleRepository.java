package rw.ac.ilpd.mis.auth.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;

import java.util.*;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByName(String name);
    @Transactional(readOnly = true)
    List<RoleEntity> findByUnitEntityId(UUID unitId);
    List<RoleEntity> findByNameLikeIgnoreCase(String name);
    boolean existsByName(String name);
}
