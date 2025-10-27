package rw.ac.ilpd.reportingservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.reportingservice.client.UserClient;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.exception.RemoteDependencyException;
import rw.ac.ilpd.reportingservice.mapper.UserPositionMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.Position;
import rw.ac.ilpd.reportingservice.model.nosql.document.UserPosition;
import rw.ac.ilpd.reportingservice.repository.nosql.PositionRepository;
import rw.ac.ilpd.reportingservice.repository.nosql.UserPositionRepository;
import rw.ac.ilpd.reportingservice.service.UserPositionService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPositionServiceImpl  implements UserPositionService {
    private final UserPositionRepository repository;
    private final UserClient userClient;
    private final PositionRepository userPositionRepository;
    private final UserPositionMapper mapper;

    @Override
    public boolean existByPositionId(String id) {
        return repository.existsByPositionId(id);
    }

    @Override
    public UserPositionsResponse createUserPosition(UserPositionsRequest request)  {
        log.info("Create user position request {}", request);
        Position positionFuture=getPositionById(request.getPositionId());
        log.info("Position found {}", positionFuture.getId());
        UserResponse userResponse=getUserResponseById(request.getUserId());
        log.info("User found {}", userResponse.getId());
        UserPosition userPosition = mapper.toUserPosition(request);
        UserPosition saved = repository.save(userPosition);
        return mapper.fromUserPositions(saved);
        }


    @Override
    public UserPositionsResponse updateUserPosition(String id, UserPositionsRequest request) {
        UserPosition userPosition = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Specified position is not found"));
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<UserResponse> userFuture = CompletableFuture.supplyAsync(() -> getUserResponseById(request.getUserId()));
            CompletableFuture<Position> positionFuture = CompletableFuture.supplyAsync(()-> getPositionById(request.getPositionId()));
            CompletableFuture.allOf(positionFuture, userFuture).join();
            executorService.shutdown();
        }
        try {
            UserPosition userPositionUpdate = mapper.toUserPositionUpdate(userPosition,request);
            UserPosition saved = repository.save(userPositionUpdate);
            return mapper.fromUserPositions(saved);
        }catch (Exception e){
            throw new RemoteDependencyException("Something went wrong while updating user position");
        }
    }

    @Override
    public Optional<UserPositionsResponse> getUserPositionById(String id) {
        return getEntity(id).map(mapper::fromUserPositions);
    }

    @Override
    public List<UserPositionsResponse> getAllUserPositions() {
        List<UserPosition>userPositions=repository.findByQuiteDateIsNullOrQuiteDateIsBefore(LocalDateTime.now());
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public PagedResponse<UserPositionsResponse> getUserPositionsWithPagination(Pageable pageable) {
        Page<UserPosition> userPositions=repository.findByQuiteDateIsNullOrQuiteDateIsBefore(LocalDateTime.now(),pageable);
        List<UserPositionsResponse>content=userPositions.stream().map(mapper::fromUserPositions).toList();
        return new PagedResponse<>(
                content,
                userPositions.getNumber(),
                userPositions.getSize(),
                userPositions.getTotalElements(),
                userPositions.getTotalPages(),
                userPositions.isLast()
        );
    }

    @Override
    public String deleteUserPosition(String id) {
        UserPosition usersPosition=getEntity(id).orElseThrow(() -> new EntityNotFoundException("Specified position is not found"));
        usersPosition.setQuiteDate(LocalDateTime.now());
        repository.save(usersPosition);
        return "Position has been archived successfully";
    }

    @Override
    public List<UserPositionsResponse> getUserPositionsByUserId(UUID userId) {
        List<UserPosition>usersPosition=repository.findByUserId(userId);
        return usersPosition.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getUserPositionsByPositionId(String positionId) {
        List<UserPosition>userPositions=repository.findByPositionIdAndQuiteDateNotNullOrQuiteDateBefore(positionId,LocalDateTime.now());
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getActiveUserPositions() {
        List<UserPosition>userPositions=repository.findByQuiteDateIsNullOrQuiteDateIsBefore(LocalDateTime.now());
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getExpiredUserPositions() {
        List<UserPosition> userPositions=repository.findByQuiteDateBefore(LocalDateTime.now());
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getUserPositionsExpiringSoon(int days) {
        List<UserPosition> userPositions=repository.findByQuittingDayBefore(days);
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getCurrentUserPositions(String userId) {
        List<UserPosition> userPositions = repository.findByUserIdAndQuiteDateIsNull(userId);
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public List<UserPositionsResponse> getCurrentUserPositionHistory(String userId) {
        List<UserPosition> userPositions=repository.findByUserId(UUID.fromString(userId));
        return userPositions.stream().map(mapper::fromUserPositions).toList();
    }

    @Override
    public String terminateUserPosition(String id) {
        UserPosition userPosition=getEntity(id).orElseThrow(() -> new EntityNotFoundException("Specified position is not found"));
         userPosition.setQuiteDate(LocalDateTime.now());
         repository.save(userPosition);
        return "User has quit to a position successfully";
    }
    private Optional<UserPosition> getEntity(String id) {
        return repository.findById(id);
    }
    private UserResponse getUserResponseById (String userId) {
        ResponseEntity<UserResponse> response = userClient.getUser(userId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        else{
        throw new RuntimeException("Failed to fetch user");
        }
    }
   private Position getPositionById(String positionId) {
       return   userPositionRepository.findByIdAndIsDeleted(positionId, false)
                       .orElseThrow(() -> new EntityNotFoundException("Position specified is not found"));
   }
}
