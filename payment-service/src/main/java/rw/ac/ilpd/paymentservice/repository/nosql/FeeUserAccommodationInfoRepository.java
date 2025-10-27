package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.paymentservice.model.nosql.FeeUserAccommodationInfo;

public interface FeeUserAccommodationInfoRepository extends MongoRepository<FeeUserAccommodationInfo, String> {
}
