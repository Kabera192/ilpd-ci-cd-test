package rw.ac.ilpd.reportingservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.reportingservice.model.nosql.document.Setting;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface SettingRepository extends MongoRepository<Setting, String> {

    Optional<Setting> findByKey(String key);

    Optional<Setting> findByKeyAndIdNot(String key, String id);

    List<Setting> findByIsDeleted(boolean deleteStatus);

    List<Setting> findByIsDeletedOrderByNameAsc(boolean deleteStatus);

    Page<Setting> findByIsDeleted(boolean deleteStatus, Pageable pageable);

    Page<Setting> findByNameContainingIgnoreCaseAndIsDeleted(String name, boolean deleteStatus, Pageable pageable);

    Page<Setting> findByKeyContainingIgnoreCaseAndIsDeleted(String key, boolean deleteStatus, Pageable pageable);

    List<Setting> findByKeyStartingWithAndIsDeleted(String keyPrefix, boolean isDeleted);

    boolean existsByKey(String key);

    boolean existsByKeyAndIdNot(String key, String id);

    long countByIsDeleted(boolean deleteStatus);

    Optional<Setting> findByIdAndIsDeleted(String id, boolean isDeleted);

    List<Setting> findByAcronymContainingIgnoreCase(String acronym);

    boolean existsByCategoryIgnoreCaseAndAcronymIgnoreCaseAndKeyIgnoreCase(String name, String acronym, @NotBlank(message = "Key cannot be null") String key);

    List<Setting> findByCategoryIgnoreCaseAndIsDeleted(String name, boolean isDeleted);

    List<Setting> findByCategoryIgnoreCaseAndIsDeletedAndNameContainingIgnoreCase(String name, boolean isDeleted, String search);
}
