package rw.ac.ilpd.reportingservice.service;

import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.reportingservice.model.nosql.document.Position;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    PositionResponse createPosition(PositionRequest request);
    PositionResponse updatePosition(String id, PositionRequest request);
    PositionResponse getPositionById(String id);
    List<PositionResponse> getAllPositions(String search);
    List<PositionResponse> getAllPositionByDeleteStatus(boolean isDeleted,String search);
    PagedResponse<PositionResponse> getPositionsWithPagination(String search, Pageable pageable);
    String deletePosition(@ValidUuid(message = "Provide a valid position identifier") String id);
    PositionResponse restoreArchive(String id);
    Optional<Position> getEntityAndDeleteStatus(String id, boolean isDeleted);
}