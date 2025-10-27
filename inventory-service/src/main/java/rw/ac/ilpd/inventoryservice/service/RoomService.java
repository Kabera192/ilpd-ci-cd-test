package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.RoomMapper;
import rw.ac.ilpd.inventoryservice.model.sql.Room;
import rw.ac.ilpd.inventoryservice.model.sql.RoomType;
import rw.ac.ilpd.inventoryservice.repository.sql.RoomRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomTypeService roomTypeService;
    private final LocationService locationService;

    public PagedResponse<RoomResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<Room> roomPage = roomRepository.findAll(pageable);
        return new PagedResponse<>(
                roomPage.getContent().stream().map(roomMapper::fromRoom).toList(),
                roomPage.getNumber(),
                roomPage.getSize(),
                roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                roomPage.isLast()
        );
    }

    public RoomResponse get(UUID id) {
        return roomMapper.fromRoom(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Room not found")));
    }

    public RoomResponse create(RoomRequest request) {
        // Check if room with same code already exists
        if (request.getCode() != null && !request.getCode().isBlank() &&
                roomRepository.existsByCode(request.getCode())) {
            throw new EntityAlreadyExists("Room with code " + request.getCode() + " already exists");
        }

        // Get RoomType entity
        RoomType roomType = roomTypeService.getEntity(UUID.fromString(request.getTypeId()))
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        // Check if location exists
        if(locationService.getEntity(request.getLocationId()).isEmpty()){
            throw new EntityNotFoundException("Location not found");
        }

        Room room = roomMapper.toRoom(request, roomType);
        return roomMapper.fromRoom(roomRepository.save(room));
    }

    public RoomResponse edit(UUID id, RoomRequest request) {
        Room existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Get RoomType entity
        RoomType roomType = roomTypeService.getEntity(UUID.fromString(request.getTypeId()))
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        // Check if location exists
        if(locationService.getEntity(request.getLocationId()).isEmpty()){
            throw new EntityNotFoundException("Location not found");
        }

        existing.setLocationId(request.getLocationId());
        existing.setName(request.getName());
        existing.setCode(request.getCode());
        existing.setRoomTypeId(roomType);

        return roomMapper.fromRoom(roomRepository.save(existing));
    }

    public RoomResponse patch(UUID id, Map<String, Object> updates) {
        Room existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Patch locationId
        if (updates.containsKey("locationId")) {
            Object value = updates.get("locationId");
            if (value instanceof String str) {
                try {
                    // Check if location exists
                    if(locationService.getEntity(str).isEmpty()){
                        throw new EntityNotFoundException("Location not found");
                    }
                    existing.setLocationId(str);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for locationId");
                }
            }
        }

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                existing.setName(name);
            }
        }

        // Patch code
        if (updates.containsKey("code")) {
            Object value = updates.get("code");
            if (value instanceof String code && !code.isBlank()) {
                existing.setCode(code);
            }
        }

        // Patch typeId
        if (updates.containsKey("typeId")) {
            Object value = updates.get("typeId");
            if (value instanceof String typeId) {
                try {
                    RoomType roomType = roomTypeService.getEntity(UUID.fromString(typeId))
                            .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));
                    existing.setRoomTypeId(roomType);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for typeId");
                }
            }
        }

        // createdAt should NOT be patched (auto-generated)
        return roomMapper.fromRoom(roomRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        roomRepository.delete(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Room not found")));
        return true;
    }

    public Optional<Room> getEntity(UUID id) {
        return roomRepository.findById(id);
    }
}