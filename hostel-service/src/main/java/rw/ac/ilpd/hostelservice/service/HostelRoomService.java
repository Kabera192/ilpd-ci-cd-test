package rw.ac.ilpd.hostelservice.service;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.hostelservice.mapper.HostelRoomMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoom;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelRoomRepository;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomResponse;
import rw.ac.ilpd.sharedlibrary.enums.RoomStatusState;
import rw.ac.ilpd.sharedlibrary.util.TextSanitizer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostelRoomService {

    private final HostelRoomRepository hostelRoomRepository;
    private final HostelRoomMapper hostelRoomMapper;
    private final HostelRoomTypeService hostelRoomTypeService;

    /**
     * Creates a new hostel room if the room type exists and room number is unique.
     *
     * @param request the request containing room details
     * @return the saved hostel room response
     */
    public HostelRoomResponse create(@Valid HostelRoomRequest request) {
        String cleanedRoomNumber = TextSanitizer.cleanExtraSpace(request.getRoomNumber()).toUpperCase();
        log.info("Creating hostel room with number: {}", cleanedRoomNumber);

        hostelRoomTypeService.getEntity(request.getRoomTypeId())
                .orElseThrow(() -> {
                    log.warn("Room type ID '{}' not found", request.getRoomTypeId());
                    return new EntityNotFoundException("Room Type Not Found. Please specify a valid room type.");
                });

        hostelRoomRepository.findByRoomNumberAndRoomStatusState(cleanedRoomNumber, RoomStatusState.ACTIVE)
                .ifPresent(existingRoom -> {
                    log.warn("Room with number '{}' already exists", existingRoom.getRoomNumber());
                    throw new EntityExistsException("Room with number '" + existingRoom.getRoomNumber() + "' already exists.");
                });

        HostelRoom newRoom = hostelRoomMapper.toHostelRoom(request);
        newRoom.setRoomStatusState(RoomStatusState.ACTIVE);
        newRoom.setRoomNumber(cleanedRoomNumber);

        HostelRoom savedRoom = hostelRoomRepository.save(newRoom);
        log.info("Room with ID '{}' created successfully", savedRoom.getId());

        return hostelRoomMapper.fromHostelRoom(savedRoom);
    }

    /**
     * Finds a hostel room by room number and status.
     *
     * @param roomNumber       the room number
     * @param roomStatusState  the status of the room
     * @return an Optional containing the room if found
     */
    public Optional<HostelRoom> findByRoomNumberAndStatus(String roomNumber, RoomStatusState roomStatusState) {
        return hostelRoomRepository.findByRoomNumberAndRoomStatusState(roomNumber, roomStatusState);
    }

    /**
     * Retrieves all hostel rooms.
     *
     * @return a list of all hostel room responses
     */
    public List<HostelRoomResponse> getAll() {
        log.info("Fetching all hostel rooms");
        return hostelRoomRepository.findAll().stream()
                .map(hostelRoomMapper::fromHostelRoom)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a hostel room by its ID.
     *
     * @param roomId the room ID
     * @return the corresponding room response
     */
    public HostelRoomResponse getById(@NotBlank(message = "Hostel room is required") String roomId) {
        log.info("Fetching hostel room with ID: {}", roomId);

        HostelRoom room = hostelRoomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.warn("Room with ID '{}' not found", roomId);
                    return new RuntimeException("Room not found");
                });

        return hostelRoomMapper.fromHostelRoom(room);
    }

    /**
     * Updates a hostel room by ID.
     * Room number change is disallowed if it conflicts with existing data.
     *
     * @param roomId the ID of the room to update
     * @param request the request containing updated data
     * @return the updated hostel room response
     */
    public HostelRoomResponse update(String roomId,@Valid HostelRoomRequest request) {
        log.info("Updating hostel room with ID: {}", roomId);

        HostelRoom existingRoom = hostelRoomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.warn("Room with ID '{}' not found", roomId);
                    return new RuntimeException("Room not found");
                });

        String newRoomNumber = TextSanitizer.cleanExtraSpace(request.getRoomNumber()).toUpperCase();
        String currentRoomNumber = TextSanitizer.cleanExtraSpace(existingRoom.getRoomNumber());

        HostelRoom updatedRoomEntity = hostelRoomMapper.tomHostelRoomUpdate(existingRoom, request);

        HostelRoom savedRoom = hostelRoomRepository
                .findByRoomNumberAndRoomStatusState(currentRoomNumber, RoomStatusState.ACTIVE)
                .map(existingActiveRoom -> {
                    if (!currentRoomNumber.equals(newRoomNumber)) {
                        log.warn("Attempt to change room number from '{}' to '{}' is not allowed", currentRoomNumber, newRoomNumber);
                        throw new EntityExistsException("Room number cannot be changed.");
                    }
                    return hostelRoomRepository.save(updatedRoomEntity);
                })
                .orElseThrow(() -> {
                    log.warn("Room number '{}' not found for update", currentRoomNumber);
                    return new EntityNotFoundException("Room number not found.");
                });

        log.info("Room with ID '{}' updated successfully", roomId);
        return hostelRoomMapper.fromHostelRoom(savedRoom);
    }

    /**
     * Deletes a hostel room by ID.
     *
     * @param roomId the ID of the room to delete
     */
    public void delete(String roomId) {
        log.info("Deleting hostel room with ID: {}", roomId);
        hostelRoomRepository.deleteById(roomId);
        log.info("Room with ID '{}' deleted successfully", roomId);
    }
}
