package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.RoomTypeMapper;
import rw.ac.ilpd.inventoryservice.model.sql.RoomType;
import rw.ac.ilpd.inventoryservice.repository.sql.RoomTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomTypeService {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomTypeMapper roomTypeMapper;

    public PagedResponse<RoomTypeResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc"))
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        else
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<RoomType> roomTypePage = roomTypeRepository.findAll(pageable);
        return new PagedResponse<>(
                roomTypePage.getContent().stream().map(roomTypeMapper::fromRoomType).toList(),
                roomTypePage.getNumber(),
                roomTypePage.getSize(),
                roomTypePage.getTotalElements(),
                roomTypePage.getTotalPages(),
                roomTypePage.isLast()
        );
    }

    public RoomTypeResponse get(UUID id) {
        return roomTypeMapper.fromRoomType(getEntity(id).orElseThrow(() ->
                new EntityNotFoundException("Room type not found with ID: " + id)));
    }

    public RoomTypeResponse create(RoomTypeRequest request) {
        // Check if room type with same name already exists
        if (roomTypeRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExists("Room type with name '" + request.getName() + "' already exists");
        }

        RoomType roomType = roomTypeMapper.toRoomType(request);
        return roomTypeMapper.fromRoomType(roomTypeRepository.save(roomType));
    }

    public RoomTypeResponse edit(UUID id, RoomTypeRequest request) {
        RoomType existing = getEntity(id).orElseThrow(() ->
                new EntityNotFoundException("Room type not found with ID: " + id));

        // Check if another room type with the same name exists (excluding current one)
        if (roomTypeRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new EntityAlreadyExists("Another room type with name '" + request.getName() + "' already exists");
        }

        existing.setName(request.getName());
        return roomTypeMapper.fromRoomType(roomTypeRepository.save(existing));
    }


    public Boolean delete(UUID id) {
        RoomType roomType = getEntity(id).orElseThrow(() ->
                new EntityNotFoundException("Room type not found with ID: " + id));
        roomTypeRepository.delete(roomType);
        return true;
    }

    public Optional<RoomType> getEntity(UUID id) {
        return roomTypeRepository.findById(id);
    }
}