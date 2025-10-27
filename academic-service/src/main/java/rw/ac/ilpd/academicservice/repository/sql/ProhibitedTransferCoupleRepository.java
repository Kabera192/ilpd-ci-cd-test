package rw.ac.ilpd.academicservice.repository.sql;

import com.mongodb.RequestContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.ProhibitedTransferCouple;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProhibitedTransferCoupleRepository extends JpaRepository<ProhibitedTransferCouple, UUID> {
    boolean existsByFromStudyModeSessionAndToStudyModeSessionAndDeletedStatus(StudyModeSession sms1, StudyModeSession sms2, boolean isDeleted);

    List<ProhibitedTransferCouple> findAllByDeletedStatus(boolean isArchived);
}
