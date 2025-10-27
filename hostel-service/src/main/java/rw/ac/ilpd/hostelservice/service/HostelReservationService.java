package rw.ac.ilpd.hostelservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.hostelservice.mapper.HostelReservationMapper;
import rw.ac.ilpd.hostelservice.mapper.HostelReservationRoomTypeCountMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservation;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelReservationRepository;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountRequest;
import rw.ac.ilpd.sharedlibrary.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HostelReservationService {
    private final HostelReservationRepository repository;
    private final HostelReservationMapper mapper;
    private final HostelReservationRoomTypeCountMapper hrrTypeCountMapper;

    public HostelReservationResponse create(HostelReservationRequest request) {
        request.setRandomQueryCode(generateRandomQueryCode());
        HostelReservation entity = mapper.toEntity(request);
        entity.setReservationStatus(ReservationStatus.ONGOING);

        // Set hostelReservationId for embedded documents
        if (entity.getHostelReservationRoomTypeCounts() != null) {
            entity.getHostelReservationRoomTypeCounts().forEach(roomTypeCount -> {
                roomTypeCount.setHostelReservationId(entity.getId());
                roomTypeCount.setCreatedAt(LocalDateTime.now());
            });
        }

        HostelReservation savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    public List<HostelReservationResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<HostelReservationResponse> findById(String id) {
        return repository.findById(id)
                .map(mapper::toResponse);
    }

    public Optional<HostelReservationResponse> findByRandomQueryCode(String queryCode) {
        return repository.findByRandomQueryCode(queryCode)
                .map(mapper::toResponse);
    }

    public List<HostelReservationResponse> findByReserverPersonId(String reserverPersonId) {
        return repository.findByReserverPersonId(reserverPersonId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<HostelReservationResponse> findByStatus(ReservationStatus status) {
        return repository.findByReservationStatus(status)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<HostelReservationResponse> findByArrivalDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByArrivalDateBetween(startDate, endDate)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<HostelReservationResponse> update(String id, HostelReservationRequest request) {
        return repository.findById(id)
                .map(existingEntity -> {
                    mapper.updateEntityFromRequest(request, existingEntity);

                    // Update embedded documents
                    if (request.getHostelReservationRoomTypeCounts() != null) {
                        List<HostelReservationRoomTypeCount> updatedRoomTypeCounts =
                                request.getHostelReservationRoomTypeCounts()
                                        .stream()
                                        .map(roomTypeCountRequest -> {
                                            HostelReservationRoomTypeCount roomTypeCount =
                                                    mapper.toRoomTypeCountEntity(roomTypeCountRequest);
                                            roomTypeCount.setHostelReservationId(id);
                                            roomTypeCount.setCreatedAt(LocalDateTime.now());
                                            return roomTypeCount;
                                        })
                                        .collect(Collectors.toList());
                        existingEntity.setHostelReservationRoomTypeCounts(updatedRoomTypeCounts);
                    }

                    HostelReservation savedEntity = repository.save(existingEntity);
                    return mapper.toResponse(savedEntity);
                });
    }

    public Optional<HostelReservationResponse> addRoomTypeCount(String reservationId, HostelReservationRoomTypeCountRequest request) {
        log.info("Adding room type count to hostel reservation with ID: {}", reservationId);
        try {
            return repository.findById(reservationId)
                    .map(existingEntity -> {
                        HostelReservationRoomTypeCount newRoomTypeCount = mapper.toRoomTypeCountEntity(request);
                        newRoomTypeCount.setHostelReservationId(reservationId);
                        newRoomTypeCount.setCreatedAt(LocalDateTime.now());

                        if (existingEntity.getHostelReservationRoomTypeCounts() == null) {
                            existingEntity.setHostelReservationRoomTypeCounts(new ArrayList<>());
                        }

                        existingEntity.getHostelReservationRoomTypeCounts().add(newRoomTypeCount);

                        HostelReservation savedEntity = repository.save(existingEntity);
                        log.info("Successfully added room type count to hostel reservation with ID: {}. Room type count ID: {}",
                                reservationId, newRoomTypeCount.getId());
                        return mapper.toResponse(savedEntity);
                    });
        } catch (Exception e) {
            log.error("Error adding room type count to hostel reservation with ID: {}", reservationId, e);
            throw e;
        }
    }

    public Optional<HostelReservationResponse> updateRoomTypeCount(String reservationId, String roomTypeCountId, HostelReservationRoomTypeCountRequest request) {
        log.info("Updating room type count with ID: {} in hostel reservation with ID: {}", roomTypeCountId, reservationId);
        try {
            return repository.findById(reservationId)
                    .map(existingEntity -> {
                        if (existingEntity.getHostelReservationRoomTypeCounts() != null) {
                            existingEntity.getHostelReservationRoomTypeCounts().stream()
                                    .filter(rtc -> rtc.getId().equals(roomTypeCountId))
                                    .findFirst()
                                    .ifPresent(roomTypeCount -> {
                                        hrrTypeCountMapper.toHostelReservationRoomTypeCountUpdate(roomTypeCount, request);
                                    });
                        }

                        HostelReservation savedEntity = repository.save(existingEntity);
                        log.info("Successfully updated room type count with ID: {} in hostel reservation with ID: {}",
                                roomTypeCountId, reservationId);
                        return mapper.toResponse(savedEntity);
                    });
        } catch (Exception e) {
            log.error("Error updating room type count with ID: {} in hostel reservation with ID: {}",
                    roomTypeCountId, reservationId, e);
            throw e;
        }
    }

    public Optional<HostelReservationResponse> removeRoomTypeCount(String reservationId, String roomTypeCountId) {
        log.info("Removing room type count with ID: {} from hostel reservation with ID: {}", roomTypeCountId, reservationId);
        try {
            return repository.findById(reservationId)
                    .map(existingEntity -> {
                        if (existingEntity.getHostelReservationRoomTypeCounts() != null) {
                            existingEntity.getHostelReservationRoomTypeCounts().removeIf(rtc -> rtc.getId().equals(roomTypeCountId));
                        }

                        HostelReservation savedEntity = repository.save(existingEntity);
                        log.info("Successfully removed room type count with ID: {} from hostel reservation with ID: {}",
                                roomTypeCountId, reservationId);
                        return mapper.toResponse(savedEntity);
                    });
        } catch (Exception e) {
            log.error("Error removing room type count with ID: {} from hostel reservation with ID: {}",
                    roomTypeCountId, reservationId, e);
            throw e;
        }
    }
    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private String generateRandomQueryCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
