package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import rw.ac.ilpd.academicservice.model.nosql.document.Activity;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityOccurrence;
import rw.ac.ilpd.sharedlibrary.enums.ActivityLevelLevels;

import java.util.List;
import java.util.UUID;

import java.util.Optional;

public interface ActivityRepository extends MongoRepository<Activity, String> {
    @Query("{'activityLevels' : {$elemMatch : {'level' :  'INTAKE', 'levelRefIid' :  ?0}}}")
    List<Activity> findsActivitiesForAnIntake(UUID intakeId);

    List<Activity> findActivitiesByActivityLevelsLevelAndActivityLevelsLevelRefId(ActivityLevelLevels level, UUID id);

    Optional<Activity> findByActivityTypeId(String id);
}
