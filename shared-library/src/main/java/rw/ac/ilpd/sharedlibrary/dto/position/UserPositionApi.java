package rw.ac.ilpd.sharedlibrary.dto.position;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.shared.config.privilege.academic.UserPositionPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;
import java.util.List;

public interface UserPositionApi {

    /**
     * Create a new user position assignment
     * POST /api/v1/user-positions
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.CREATE_USER_POSITION})
    @PostMapping(UserPositionPriv.CREATE_USER_POSITION_PATH)
    ResponseEntity<UserPositionsResponse> createUserPosition(@Valid @RequestBody UserPositionsRequest request);

    /**
     * Get all user positions
     * GET /api/v1/user-positions
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_ALL_USER_POSITIONS})
    @GetMapping(UserPositionPriv.GET_ALL_USER_POSITIONS_PATH)
    ResponseEntity<List<UserPositionsResponse>> getAllUserPositions();

    /**
     * Get user positions with pagination
     * GET /api/v1/user-positions/paginated?page=0&size=10&sort=createdAt,desc
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_USER_POSITIONS_PAGINATED})
    @GetMapping(UserPositionPriv.GET_USER_POSITIONS_PAGINATED_PATH)
    ResponseEntity<PagedResponse<UserPositionsResponse>> getUserPositionsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection);

    /**
     * Get user position by ID
     * GET /api/v1/user-positions/{id}
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_USER_POSITION_BY_ID})
    @GetMapping(UserPositionPriv.GET_USER_POSITION_BY_ID_PATH)
    ResponseEntity<UserPositionsResponse> getUserPositionById(@PathVariable String id);

    /**
     * Update user position
     * PUT /api/v1/user-positions/{id}
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.UPDATE_USER_POSITION_PATH})
    @PutMapping(UserPositionPriv.UPDATE_USER_POSITION)
    ResponseEntity<UserPositionsResponse> updateUserPosition(
            @PathVariable String id,
            @Valid @RequestBody  UserPositionsRequest request);

    /**
     * Delete user position
     * DELETE /api/v1/user-positions/{id}
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.DELETE_USER_POSITION})
    @DeleteMapping(UserPositionPriv.DELETE_USER_POSITION_PATH)
    ResponseEntity<String> deleteUserPosition(@PathVariable String id);

    /**
     * Get user positions by user ID
     * GET /api/v1/user-positions/user/{userId}
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_USER_POSITIONS_BY_USER_ID})
    @GetMapping(UserPositionPriv.GET_USER_POSITIONS_BY_USER_ID_PATH)
    ResponseEntity<List<UserPositionsResponse>> getUserPositionsByUserId(@PathVariable String userId);

    /**
     * Get user positions by position ID
     * GET /api/v1/user-positions/position/{positionId}
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_USER_POSITIONS_BY_POSITION_ID})
    @GetMapping(UserPositionPriv.GET_USER_POSITIONS_BY_POSITION_ID_PATH)
    ResponseEntity<List<UserPositionsResponse>> getUserPositionsByPositionId(@PathVariable String positionId);

    /**
     * Get active user positions (no quit date or quit date in future)
     * GET /api/v1/user-positions/active
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_ACTIVE_USER_POSITIONS})
    @GetMapping(UserPositionPriv.GET_ACTIVE_USER_POSITIONS_PATH)
    ResponseEntity<List<UserPositionsResponse>> getActiveUserPositions();

    /**
     * Get expired user positions
     * GET /api/v1/user-positions/expired
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_EXPIRED_USER_POSITIONS})
    @GetMapping(UserPositionPriv.GET_EXPIRED_USER_POSITIONS_PATH)
    ResponseEntity<List<UserPositionsResponse>> getExpiredUserPositions();

    /**
     * Get user positions expiring soon
     * GET /api/v1/user-positions/expiring-soon?days=30
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_USER_POSITIONS_EXPIRING_SOON})
    @GetMapping(UserPositionPriv.GET_USER_POSITIONS_EXPIRING_SOON_PATH)
    ResponseEntity<List<UserPositionsResponse>> getUserPositionsExpiringSoon(
            @RequestParam(defaultValue = "30") int days);

    /**
     * Get current user position for a specific user
     * GET /api/v1/user-positions/user/{userId}/current
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.GET_CURRENT_USER_POSITION})
    @GetMapping(UserPositionPriv.GET_CURRENT_USER_POSITION_PATH)
    ResponseEntity<List<UserPositionsResponse>> getCurrentUserPosition(@PathVariable
                                                                       @ValidUuid(message = "Provide  a valid user format") String userId);


    /**
     * Terminate user position
     * PATCH /api/v1/user-positions/{id}/terminate
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,UserPositionPriv.TERMINATE_USER_POSITION})
    @PatchMapping(UserPositionPriv.TERMINATE_USER_POSITION_PATH)
    ResponseEntity<String> terminateUserPosition(
            @PathVariable String id);

}
