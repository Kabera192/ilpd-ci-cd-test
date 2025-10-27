package rw.ac.ilpd.reportingservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rw.ac.ilpd.reportingservice.model.nosql.document.Position;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository  extends MongoRepository<Position, String>
{
    boolean existsByNameAndAbbreviationAndIsDeleted(@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name, @NotBlank(message = "Abbreviation is required") @Size(min = 1, max = 10, message = "Abbreviation must be between 1 and 10 characters") String abbreviation, boolean b);

    List<Position> findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCase(String search, String search1);

    List<Position> findByIsDeleted(boolean isDeleted);

    List<Position> findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCaseAndIsDeleted(String search, String search1, boolean isDeleted);

    Page<Position> findByNameContainingIgnoreCaseOrAbbreviationContainingIgnoreCase(String search, String search1, Pageable pageable);

    Optional<Position> findByIdAndIsDeleted(String id, boolean isDeleted);
}
