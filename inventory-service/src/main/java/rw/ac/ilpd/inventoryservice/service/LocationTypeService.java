package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.LocationTypeMapper;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;
import rw.ac.ilpd.inventoryservice.repository.nosql.LocationTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationTypeService {
    private final LocationTypeRepository locationTypeRepository;
    private final LocationTypeMapper locationTypeMapper;

    public PagedResponse<LocationTypeResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        // Only fetch non-deleted location types
        Page<LocationType> locationTypePage = locationTypeRepository.findByDeleteStatus(false, pageable);
        return new PagedResponse<>(
                locationTypePage.getContent().stream().map(locationTypeMapper::fromLocationType).toList(),
                locationTypePage.getNumber(),
                locationTypePage.getSize(),
                locationTypePage.getTotalElements(),
                locationTypePage.getTotalPages(),
                locationTypePage.isLast()
        );
    }

    public LocationTypeResponse get(String id) {
        // Only return if not deleted
        return locationTypeMapper.fromLocationType(
                getActiveEntity(id).orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"))
        );
    }

    public LocationTypeResponse create(LocationTypeRequest request) {
        // Check if location type with same name already exists (and not deleted)
        if (locationTypeRepository.existsByNameAndDeleteStatus(request.getName(), false)) {
            throw new EntityAlreadyExists("LocationType with name " + request.getName() + " already exists");
        }

        LocationType locationType = locationTypeMapper.toLocationType(request);
        return locationTypeMapper.fromLocationType(locationTypeRepository.save(locationType));
    }

    public LocationTypeResponse edit(String id, LocationTypeRequest request) {
        LocationType existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));

        // Check if another location type with the new name already exists
        if (!existing.getName().equals(request.getName()) &&
                locationTypeRepository.existsByNameAndDeleteStatus(request.getName(), false)) {
            throw new EntityAlreadyExists("LocationType with name " + request.getName() + " already exists");
        }

        existing.setName(request.getName());
        return locationTypeMapper.fromLocationType(locationTypeRepository.save(existing));
    }

    public LocationTypeResponse patch(String id, Map<String, Object> updates) {
        LocationType existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                // Check if another location type with the new name already exists
                if (!existing.getName().equals(name) &&
                        locationTypeRepository.existsByNameAndDeleteStatus(name, false)) {
                    throw new EntityAlreadyExists("LocationType with name " + name + " already exists");
                }
                existing.setName(name);
            }
        }

        // Patch deleteStatus - but we shouldn't allow patching this directly
        // createdAt should NOT be patched (auto-generated)

        return locationTypeMapper.fromLocationType(locationTypeRepository.save(existing));
    }

    public Boolean delete(String id) {
        LocationType existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));

        // Soft delete by setting deleteStatus to true
        existing.setDeleteStatus(true);
        locationTypeRepository.save(existing);
        return true;
    }

    public Optional<LocationType> getEntity(String id) {
        return locationTypeRepository.findById(id);
    }

    public Optional<LocationType> getActiveEntity(String id) {
        return locationTypeRepository.findByIdAndDeleteStatus(id, false);
    }
    public int loadLocationTypeInitList(List<LocationTypeRequest> locationTypeRequests) {
        List<LocationType> unsaveLocationTypes=locationTypeRequests.stream()
                .filter(lt->!locationTypeRepository.existsByName(lt.getName()))
                .map(locationTypeMapper::toLocationType).toList();
        List<LocationType> locationTypes=new ArrayList<>();
        if(!unsaveLocationTypes.isEmpty()){
            return 0;
        }else{
            locationTypes= locationTypeRepository.saveAll(unsaveLocationTypes);
            return locationTypes.size();
        }

    }

    public LocationType getEntityByName(String locationTypeName) {
        Optional<LocationType>locationType=locationTypeRepository.findByNameContainingIgnoreCase(locationTypeName).stream().findFirst();
        return locationType.orElseThrow(() -> new EntityNotFoundException("LocationType with name " + locationTypeName + " not found"));
    }
}