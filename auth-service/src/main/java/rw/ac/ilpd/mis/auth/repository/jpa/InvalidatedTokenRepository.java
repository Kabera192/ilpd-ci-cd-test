package rw.ac.ilpd.mis.auth.repository.jpa;

import rw.ac.ilpd.mis.auth.entity.jpa.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 13/07/2025
 */
@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, UUID> {
    boolean existsByIdAndUserId(UUID jwtid, UUID userId);
}