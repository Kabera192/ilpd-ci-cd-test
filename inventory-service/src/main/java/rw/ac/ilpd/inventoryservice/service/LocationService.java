package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.inventoryservice.bootstrap.location.InitCampus;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.LocationMapper;
import rw.ac.ilpd.inventoryservice.model.nosql.document.Location;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;
import rw.ac.ilpd.inventoryservice.repository.nosql.LocationRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationResponse;
import rw.ac.ilpd.sharedlibrary.enums.BlockType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationTypeService locationTypeService;

    public PagedResponse<LocationResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        // Only fetch non-deleted locations
        Page<Location> locationPage = locationRepository.findByDeleteStatus(false, pageable);
        return new PagedResponse<>(
                locationPage.getContent().stream().map(locationMapper::fromLocation).toList(),
                locationPage.getNumber(),
                locationPage.getSize(),
                locationPage.getTotalElements(),
                locationPage.getTotalPages(),
                locationPage.isLast()
        );
    }

    public LocationResponse get(String id) {
        // Only return if not deleted
        return locationMapper.fromLocation(
                getActiveEntity(id).orElseThrow(() -> new EntityNotFoundException("Location not found or deleted"))
        );
    }

    public LocationResponse create(LocationRequest request) {
        // Check if location with same name already exists (and not deleted)
        if (locationRepository.existsByNameAndDeleteStatus(request.getName(), false)) {
            throw new EntityAlreadyExists("Location with name " + request.getName() + " already exists");
        }

        // Get LocationType entity
        LocationType locationType = locationTypeService.getActiveEntity(request.getTypeId())
                .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));

        // Get parent location if specified
        Location parentLocation = null;
        if (request.getParentLocationId() != null && !request.getParentLocationId().isBlank()) {
            parentLocation = getActiveEntity(request.getParentLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Location not found or deleted"));
        }

        // Validate block type if specified
        if (request.getBlocType() != null && !request.getBlocType().isBlank()) {
            try {
                BlockType.valueOf(request.getBlocType());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid block type. Must be INTERNAL or EXTERNAL");
            }
        }

        Location location = locationMapper.toLocation(request, locationType, parentLocation);
        return locationMapper.fromLocation(locationRepository.save(location));
    }

    public LocationResponse edit(String id, LocationRequest request) {
        Location existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found or deleted"));

        // Check if another location with the new name already exists
        if (!existing.getName().equals(request.getName()) &&
                locationRepository.existsByNameAndDeleteStatus(request.getName(), false)) {
            throw new EntityAlreadyExists("Location with name " + request.getName() + " already exists");
        }

        // Get LocationType entity
        LocationType locationType = locationTypeService.getActiveEntity(request.getTypeId())
                .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));

        // Get parent location if specified
        Location parentLocation = null;
        if (request.getParentLocationId() != null && !request.getParentLocationId().isBlank()) {
            parentLocation = getActiveEntity(request.getParentLocationId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent Location not found or deleted"));
        }

        // Validate block type if specified
        if (request.getBlocType() != null && !request.getBlocType().isBlank()) {
            try {
                BlockType.valueOf(request.getBlocType());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid block type. Must be INTERNAL or EXTERNAL");
            }
        }

        existing.setName(request.getName());
        existing.setType(locationType);
        existing.setParentLocationId(request.getParentLocationId());
        existing.setLatitude(request.getLatitude());
        existing.setLongitude(request.getLongitude());
        existing.setBlockType(request.getBlocType() != null ?
                BlockType.valueOf(request.getBlocType()) : null);

        return locationMapper.fromLocation(locationRepository.save(existing));
    }

    public LocationResponse patch(String id, Map<String, Object> updates) {
        Location existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found or deleted"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                // Check if another location with the new name already exists
                if (!existing.getName().equals(name) &&
                        locationRepository.existsByNameAndDeleteStatus(name, false)) {
                    throw new EntityAlreadyExists("Location with name " + name + " already exists");
                }
                existing.setName(name);
            }
        }

        // Patch typeId
        if (updates.containsKey("typeId")) {
            Object value = updates.get("typeId");
            if (value instanceof String typeId) {
                LocationType locationType = locationTypeService.getActiveEntity(typeId)
                        .orElseThrow(() -> new EntityNotFoundException("LocationType not found or deleted"));
                existing.setType(locationType);
            }
        }

        // Patch parentLocationId
        if (updates.containsKey("parentLocationId")) {
            Object value = updates.get("parentLocationId");
            if (value instanceof String parentId) {
                if (parentId.isBlank()) {
                    existing.setParentLocationId(null);
                } else {
                    Location parentLocation = getActiveEntity(parentId)
                            .orElseThrow(() -> new EntityNotFoundException("Parent Location not found or deleted"));
                    existing.setParentLocationId(parentId);
                }
            }
        }

        // Patch latitude
        if (updates.containsKey("latitude")) {
            Object value = updates.get("latitude");
            if (value instanceof BigDecimal latitude) {
                existing.setLatitude(latitude);
            }
        }

        // Patch longitude
        if (updates.containsKey("longitude")) {
            Object value = updates.get("longitude");
            if (value instanceof BigDecimal longitude) {
                existing.setLongitude(longitude);
            }
        }

        // Patch blocType
        if (updates.containsKey("blocType")) {
            Object value = updates.get("blocType");
            if (value instanceof String blocType && !blocType.isBlank()) {
                try {
                    existing.setBlockType(BlockType.valueOf(blocType));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid block type. Must be INTERNAL or EXTERNAL");
                }
            }
        }

        // createdAt and deleteStatus should NOT be patched
        return locationMapper.fromLocation(locationRepository.save(existing));
    }

    public Boolean delete(String id) {
        Location existing = getActiveEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found or deleted"));

        // Soft delete by setting deleteStatus to true
        existing.setDeleteStatus(true);
        locationRepository.save(existing);
        return true;
    }

    public Optional<Location> getEntity(String id) {
        return locationRepository.findById(id);
    }

    public Optional<Location> getActiveEntity(String id) {
        return locationRepository.findByIdAndDeleteStatus(id, false);
    }
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int saveInitialListOfCountries(String locationTypeName, List<String> countries) {
        LocationType locationType=locationTypeService.getEntityByName(locationTypeName);
        List<Location>locations=countries.stream().filter(lcName->!locationRepository.existsByNameContainingIgnoreCaseAndTypeId(lcName,locationType.getId()))
                .map(lt-> new Location(lt,locationType)).toList();
        if(!locations.isEmpty()){
            List<Location> locationList = locationRepository.saveAll(locations);
            return locationList.size();
        }else{
            return 0;
        }
    }
    @Transactional(readOnly = true)
    public List<LocationResponse> getAllLocationByLocationTypeName(String search,String locationTypeName) {
        LocationType locationType=locationTypeService.getEntityByName(locationTypeName);
        List<Location>locationList=new ArrayList<>();
        if(search.isBlank()){
            locationList=locationRepository.findByTypeId(locationType.getId());
        }else {
            locationList=locationRepository.findByTypeIdAndNameContainingIgnoreCase(locationType.getId(),search);
        }

        return locationList.stream().map(locationMapper::fromLocation).toList();
    }

    public int saveInitialListOfCampus(String campus, List<InitCampus.Campus> campusList) {
        LocationType locationType=locationTypeService.getEntityByName(campus);
        List<Location> campuses = campusList.stream()
                .filter(lc -> !locationRepository.existsByNameContainingIgnoreCaseAndTypeId(lc.name(), locationType.getId()))
                .map(cs-> Location.builder()
                        .name(cs.name())
                        .type(locationType)
                        .latitude(cs.latitude())
                        .longitude(cs.longitude())
                        .build())
                .toList();
        if(!campuses.isEmpty()){
            return locationRepository.saveAll(campuses).size();
        }else {
            return 0;
        }
    }
}