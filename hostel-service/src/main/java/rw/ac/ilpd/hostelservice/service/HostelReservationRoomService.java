package rw.ac.ilpd.hostelservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.hostelservice.mapper.HostelReservationRoomMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoom;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelReservationRoomRepository;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomUpdateRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@Slf4j
public class HostelReservationRoomService {

    private final HostelReservationRoomRepository repository;
    private final HostelReservationRoomMapper mapper;

    public HostelReservationRoomService(HostelReservationRoomRepository repository,
                                        HostelReservationRoomMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public HostelReservationRoomResponse create(HostelReservationRoomRequest request) {
        log.info("Creating new hostel reservation room with reservation: {}", request.getReservation());

        // Generate unique reservation code if not provided
        if (request.getReservationCode() == null || request.getReservationCode().isEmpty()) {
            request.setReservationCode(generateUniqueReservationCode());
        }

        // Validate reservation code uniqueness
        if (repository.existsByRandomCodeInfoFillIgnoreCase(request.getReservationCode())) {
            throw new IllegalArgumentException("Reservation code already exists: " + request.getReservationCode());
        }

        HostelReservationRoom entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        HostelReservationRoom saved = repository.save(entity);
        log.info("Successfully created hostel reservation room with id: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HostelReservationRoomResponse findById(String id) {
        log.info("Finding hostel reservation room by id: {}", id);

        HostelReservationRoom entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Hostel reservation room not found with id: " + id));

        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public HostelReservationRoomResponse findByReservationCode(String reservationCode) {
        log.info("Finding hostel reservation room by reservation code: {}", reservationCode);

        HostelReservationRoom entity = repository.findByRandomCodeInfoFill(reservationCode)
                .orElseThrow(() -> new EntityNotFoundException("Hostel reservation room not found with reservation code: " + reservationCode));

        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<HostelReservationRoomResponse> findAll() {
        log.info("Finding all hostel reservation rooms");

        List<HostelReservationRoom> entities = repository.findAll();
        return mapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<HostelReservationRoomResponse> findByReservationId(UUID reservationId) {
        log.info("Finding hostel reservation rooms by reservation id: {}", reservationId);

        List<HostelReservationRoom> entities = repository.findByHostelReservationRoomTypeCountId(reservationId);
        return mapper.toResponseList(entities);
    }

    @Transactional(readOnly = true)
    public List<HostelReservationRoomResponse> findByClientId(UUID clientId) {
        log.info("Finding hostel reservation rooms by client id: {}", clientId);

        List<HostelReservationRoom> entities = repository.findByClientId(clientId);
        return mapper.toResponseList(entities);
    }

    public HostelReservationRoomResponse update(String id, HostelReservationRoomUpdateRequest request) {
        log.info("Updating hostel reservation room with id: {}", id);

        HostelReservationRoom entity = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Hostel reservation room not found with id: " + id));

        mapper.updateEntityFromRequest(request, entity);

        HostelReservationRoom updated = repository.save(entity);
        log.info("Successfully updated hostel reservation room with id: {}", id);

        return mapper.toResponse(updated);
    }

    public void delete(String id) {
        log.info("Deleting hostel reservation room with id: {}", id);

        if (!repository.existsById(UUID.fromString(id))) {
            throw new EntityNotFoundException("Hostel reservation room not found with id: " + id);
        }

        repository.deleteById(UUID.fromString(id));
        log.info("Successfully deleted hostel reservation room with id: {}", id);
    }
public List<HostelReservationRoomTypeCount> findFirstRoomTypeCountByRoomTypeId(String roomTypeId){
        return repository.findFirstRoomTypeCountByRoomTypeId(roomTypeId);
}
    private String generateUniqueReservationCode() {
        String code;
        do {
            code = "HRR-" + System.currentTimeMillis() + "-" +
                    ThreadLocalRandom.current().nextInt(1000, 9999);
        } while (repository.existsByRandomCodeInfoFillIgnoreCase(code));

        return code;
    }
}

