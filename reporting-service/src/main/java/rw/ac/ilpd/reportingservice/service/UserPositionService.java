package rw.ac.ilpd.reportingservice.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPositionService {
    boolean existByPositionId(String id);
        UserPositionsResponse createUserPosition(@Valid UserPositionsRequest request);
        UserPositionsResponse updateUserPosition(String id, @Valid  UserPositionsRequest request);
        Optional<UserPositionsResponse> getUserPositionById(String id);
        List<UserPositionsResponse> getAllUserPositions();
        PagedResponse<UserPositionsResponse> getUserPositionsWithPagination(Pageable pageable);
        String deleteUserPosition(String id);
        // Specific business logic methods
        List<UserPositionsResponse> getUserPositionsByUserId(UUID userId);
        List<UserPositionsResponse> getUserPositionsByPositionId(String positionId);
        List<UserPositionsResponse> getActiveUserPositions();
        List<UserPositionsResponse> getExpiredUserPositions();
        List<UserPositionsResponse> getUserPositionsExpiringSoon(int days);
        List<UserPositionsResponse> getCurrentUserPositions(@ValidUuid(message = "Provide correct user format") String  userId);
        List<UserPositionsResponse> getCurrentUserPositionHistory(@ValidUuid(message = "Provide correct user format") String  userId);
        String terminateUserPosition(String id);
}
