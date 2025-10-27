package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.nosql.InstallmentModule;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstallmentModuleRepository extends MongoRepository<InstallmentModule, String> {
    Optional<InstallmentModule> findByCurriculumModuleId(UUID uuid);
}
