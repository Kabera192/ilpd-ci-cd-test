package rw.ac.ilpd.mis.auth.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    Page<UserEntity> findAll(Pageable pageable);

    boolean existsByUsername(String superadmin);

    Page<UserEntity> findByRolesIn(Collection<Set<RoleEntity>> roles, Pageable pageable);

    Page<UserEntity> findByRoles(RoleEntity role, Pageable pageable);
}
