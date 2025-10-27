package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationDistinctionGroup;
@Repository
public interface DeliberationDistinctionGroupRepository extends MongoRepository<DeliberationDistinctionGroup, String> {
}
