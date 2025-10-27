package rw.ac.ilpd.mis.auth.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2025
 */

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, UUID> {

    Optional<PrivilegeEntity> findByName(String name);
    List<PrivilegeEntity> findByNameLikeIgnoreCase(String name);
    Page<PrivilegeEntity> findAll(Pageable pageable);
    boolean existsByName(String name);
}
