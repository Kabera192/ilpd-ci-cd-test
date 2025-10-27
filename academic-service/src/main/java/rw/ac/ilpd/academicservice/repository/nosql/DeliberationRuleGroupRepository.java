package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationRuleGroup;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.util.Collection;
import java.util.List;

public interface DeliberationRuleGroupRepository extends MongoRepository<DeliberationRuleGroup, String> {
    List<DeliberationRuleGroup> findByStatus(ValidityStatus status);
}
