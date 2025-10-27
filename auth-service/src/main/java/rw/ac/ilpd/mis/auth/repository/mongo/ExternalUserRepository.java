package rw.ac.ilpd.mis.auth.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.mis.auth.entity.mongo.ExternalUserEntity;

import java.util.Optional;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */
public interface ExternalUserRepository extends MongoRepository<ExternalUserEntity, String> {

    Page<ExternalUserEntity> findByNameOrEmailOrPhoneNumber(String search, String search1, String search2, Pageable pageable);


    Optional<ExternalUserEntity> findByIdAndCreatedBy(String id, String currentUserId);


    Page<ExternalUserEntity> findByCreatedByAndType(String currentUserId, String type, Pageable pageable);

    Page<ExternalUserEntity> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseAndCreatedByAndType(String search, String search1, String search2, String currentUserId, String upperCase, Pageable pageable);
}
