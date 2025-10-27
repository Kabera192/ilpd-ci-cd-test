package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ShortCourseTopicRepository  extends JpaRepository<ShortCourseTopic, UUID> {
     Optional<ShortCourseTopic> findByIntakeId(UUID uuid);

    List<ShortCourseTopic> findByIsDeleted(boolean b);

    List<ShortCourseTopic> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(String search, String search1, boolean b);

    List<ShortCourseTopic> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search, String search1);

    List<ShortCourseTopic> findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(boolean b, String search, String search1);

    Page<ShortCourseTopic> findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(boolean b,
            String search, String search2, Pageable pageable);

    Page<ShortCourseTopic> findByIsDeleted(boolean b, Pageable pageable);

    Page<ShortCourseTopic> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search,
            String search2, Pageable pageable);

    Page<ShortCourseTopic> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(String search,
            String search2, boolean b, Pageable pageable);
}
