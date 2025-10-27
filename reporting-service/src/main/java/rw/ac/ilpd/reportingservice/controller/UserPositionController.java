package rw.ac.ilpd.reportingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.ilpd.reportingservice.service.UserPositionService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionApi;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user-positions")
public class UserPositionController implements UserPositionApi {
    private final UserPositionService userPositionService;

    /**
     * Create a new user position assignment
     * POST /api/v1/user-positions
     */
    @Override
    public ResponseEntity<UserPositionsResponse> createUserPosition(UserPositionsRequest request) {
        try {
            UserPositionsResponse response = userPositionService.createUserPosition(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all user positions
     * GET /api/v1/user-positions
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getAllUserPositions() {
        try {
            List<UserPositionsResponse> userPositions = userPositionService.getAllUserPositions();
            return new ResponseEntity<>(userPositions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get user positions with pagination
     * GET /api/v1/user-positions/paginated?page=0&size=10&sort=createdAt,desc
     */
    @Override
    public ResponseEntity<PagedResponse<UserPositionsResponse>> getUserPositionsWithPagination(
             int page,
              int size,
             String sortBy,
              String sortDirection) {

        try {
            Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);

            PagedResponse<UserPositionsResponse> userPositions = userPositionService.getUserPositionsWithPagination(pageable);
            return new ResponseEntity<>(userPositions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get user position by ID
     * GET /api/v1/user-positions/{id}
     */
    @Override
    public ResponseEntity<UserPositionsResponse> getUserPositionById(String id) {
        try {
            Optional<UserPositionsResponse> userPosition = userPositionService.getUserPositionById(id);
            return userPosition.map(up -> new ResponseEntity<>(up, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update user position
     * PUT /api/v1/user-positions/{id}
     */
    @Override
    public ResponseEntity<UserPositionsResponse> updateUserPosition(
            String id,
             UserPositionsRequest request) {

            UserPositionsResponse updatedUserPosition = userPositionService.updateUserPosition(id, request);
            return new ResponseEntity<>(updatedUserPosition, HttpStatus.OK);
    }

    /**
     * Delete user position
     * DELETE /api/v1/user-positions/{id}
     */
    @Override
    public ResponseEntity<String> deleteUserPosition(String id) {

        String response = userPositionService.deleteUserPosition(id);
        return ResponseEntity.ok(response);

    }

    /**
     * Get user positions by user ID
     * GET /api/v1/user-positions/user/{userId}
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getUserPositionsByUserId(String userId) {
        try {
            List<UserPositionsResponse> userPositions = userPositionService.getUserPositionsByUserId(UUID.fromString(userId));
            return new ResponseEntity<>(userPositions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get user positions by position ID
     * GET /api/v1/user-positions/position/{positionId}
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getUserPositionsByPositionId( String positionId) {
        try {
            List<UserPositionsResponse> userPositions = userPositionService.getUserPositionsByPositionId(positionId);
            return new ResponseEntity<>(userPositions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get active user positions (no quit date or quit date in future)
     * GET /api/v1/user-positions/active
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getActiveUserPositions() {
            List<UserPositionsResponse> activePositions = userPositionService.getActiveUserPositions();
            return new ResponseEntity<>(activePositions, HttpStatus.OK);
    }

    /**
     * Get expired user positions
     * GET /api/v1/user-positions/expired
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getExpiredUserPositions() {
            List<UserPositionsResponse> expiredPositions = userPositionService.getExpiredUserPositions();
            return new ResponseEntity<>(expiredPositions, HttpStatus.OK);
    }

    /**
     * Get user positions expiring soon
     * GET /api/v1/user-positions/expiring-soon?days=30
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getUserPositionsExpiringSoon(
              int days) {
            List<UserPositionsResponse> expiringSoon = userPositionService.getUserPositionsExpiringSoon(days);
            return new ResponseEntity<>(expiringSoon, HttpStatus.OK);

    }

    /**
     * Get current user position for a specific user
     * GET /api/v1/user-positions/user/{userId}/current
     */
    @Override
    public ResponseEntity<List<UserPositionsResponse>> getCurrentUserPosition(String userId) {
            List<UserPositionsResponse> currentPositions = userPositionService.getCurrentUserPositions(userId);
            return ResponseEntity.ok(currentPositions);
    }



    /**
     * Terminate user position
     * PATCH /api/v1/user-positions/{id}/terminate
     */
    @Override
    public ResponseEntity<String> terminateUserPosition( String id) {
        String terminateUserPosition = userPositionService.terminateUserPosition(id);
        return  ResponseEntity.ok(terminateUserPosition);
    }

}
