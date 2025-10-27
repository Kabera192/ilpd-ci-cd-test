package rw.ac.ilpd.mis.auth.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.mis.auth.entity.jpa.UnitEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project misold
 * @date 06/08/2025
 */
public interface UnitRepository extends JpaRepository<UnitEntity, UUID> {

    Optional<UnitEntity> findByName(String name);
    @Transactional(readOnly = true)
    Optional<UnitEntity> findById (UUID unitId);
    List<UnitEntity> findByNameLikeIgnoreCase(String name);
}