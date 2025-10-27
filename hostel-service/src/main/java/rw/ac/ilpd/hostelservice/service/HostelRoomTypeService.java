package rw.ac.ilpd.hostelservice.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.hostelservice.exception.RoomCapacityExceededException;
import rw.ac.ilpd.hostelservice.integration.client.DocumentClient;
import rw.ac.ilpd.hostelservice.mapper.HostelRoomImageMapper;
import rw.ac.ilpd.hostelservice.mapper.HostelRoomTypeMapper;
import rw.ac.ilpd.hostelservice.mapper.HostelRoomTypePricingMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoomType;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomImage;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomTypePricing;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelRoomTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.*;
import rw.ac.ilpd.sharedlibrary.enums.ClientType;
import rw.ac.ilpd.sharedlibrary.enums.PricingStatus;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostelRoomTypeService {
    private final HostelRoomTypeRepository repository;
    private final HostelRoomTypeMapper mapper;
    private final HostelRoomImageMapper htImageMapper;
    private final HostelRoomTypePricingMapper htPricingMapper;
//    private final HostelReservationRoomService  reservationRoomService;
    private final DocumentClient documentClient;

    // Create
    public HostelRoomTypeResponse createHostelRoomType(HostelRoomTypeRequest request) {
        getEntityByType(request.getType())
        .ifPresent(entity -> {
            throw new EntityExistsException("The specified hostel room type already exists. Duplicate entries are not allowed.");
        }
        );
        HostelRoomType entity =mapper.toHostelRoomType(request);
//        check that document images has been provided before calling the feign client
        if(request.getImages() != null&&!request.getImages().isEmpty()) {
            List<DocumentResponse> documentResponses = documentClient
                    .saveDocumentList(ObjectListStorageRequest.builder()
                    .attachedFiles(request.getImages())
                    .bucketName(ObjectUploadPath.Hostel.BASE)
                    .objectPath(ObjectUploadPath.Hostel.ROOM_TYPE_IMAGE)
                    .build());

            List<HostelRoomImage> hostelRoomImages = documentResponses
                    .stream()
                    .map(dr -> HostelRoomImage.builder().imageDocId(dr.getId()).build())
                    .toList();
            entity.setHostelRoomImages(hostelRoomImages);
        }
        HostelRoomType saved = repository.save(entity);
        return mapper.fromHostelRoomType(saved);
    }
    public Optional<HostelRoomType>getEntity(String id){
        return repository.findById(id);
    }
    public Optional<HostelRoomType>getEntityByType(String name){
        return repository.findByType(name);
    }
    // Read - Get by ID
    public Optional<HostelRoomTypeResponse> getHostelRoomTypeById(String id) {
        return repository.findById(id)
                .map(mapper::fromHostelRoomType);
    }
    //Read -Get all by pricing status
//    public  List<HostelRoomTypeResponse>getAllHostelRoomTypesHavingSpecifiedPricingStatus(String pricingStatus){
    public List<HostelRoomTypeResponse> getAllHostelRoomTypesHavingSpecifiedPricingStatus(String pricingStatus) {
        List<HostelRoomType> status = repository.findAvailablePriceOnParticularHostelType(PricingStatus.valueOf(pricingStatus));

        // 1. Collect imageDocIds from all hostelRoomImages (non-null)
        List<String> imageIds = status.stream()
                .flatMap(hostelRoomType -> hostelRoomType.getHostelRoomImages().stream())
                .filter(Objects::nonNull)
                .map(HostelRoomImage::getImageDocId)
                .filter(Objects::nonNull)
                .toList();

        // 2. Fetch documents from the document service
        List<DocumentResponse> documentResponses = documentClient.findByIds(new HashSet<>(imageIds));

        // 3. Build a map from imageDocId to url
        Map<String, String> imageIdToUrlMap = documentResponses.stream()
                .collect(Collectors.toMap(DocumentResponse::getId, DocumentResponse::getUrl));

        // 4. Map each HostelRoomType to HostelRoomTypeResponse and replace URLs in images
        return status.stream()
                .map(hostelRoomType -> {
                    HostelRoomTypeResponse response = mapper.fromHostelRoomType(hostelRoomType);

                    if (response.getHostelRoomImages() != null) {
                        List<HostelRoomImageResponse> updatedImages = response.getHostelRoomImages().stream()
                                .map(imageResponse -> {
                                    String url = imageIdToUrlMap.get(imageResponse.getImageDocId());
                                    return HostelRoomImageResponse.builder()
                                            .id(imageResponse.getId())
                                            .imageDocId(imageResponse.getImageDocId())
                                            .url(url != null ? url : imageResponse.getUrl()) // fallback to old URL if none found
                                            .build();
                                })
                                .toList();
                        response.setHostelRoomImages(updatedImages);
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    // Read - Get all
//        public List<HostelRoomTypeResponse> getAllHostelRoomTypesWithHavingPricingStatusAndClientType(String pricingStatus,String clientType) {
//            List<HostelRoomType> roomTypes = repository.findRoomTypesWithActiveClientPrices(PricingStatus.valueOf(pricingStatus),ClientType.valueOf(clientType));
//            return roomTypes.stream().map(mapper::fromHostelRoomType).collect(Collectors.toList());
//        }
    public List<HostelRoomTypeResponse> getAllHostelRoomTypesWithHavingPricingStatusAndClientType(String pricingStatus, String clientType) {
        List<HostelRoomType> roomTypes = repository.findRoomTypesWithActiveClientPrices(
                PricingStatus.valueOf(pricingStatus), ClientType.valueOf(clientType));

        // 1. Collect imageDocIds
        List<String> imageIds = roomTypes.stream()
                .flatMap(hostelRoomType -> hostelRoomType.getHostelRoomImages().stream())
                .filter(Objects::nonNull)
                .map(HostelRoomImage::getImageDocId)
                .filter(Objects::nonNull)
                .toList();

        // 2. Fetch DocumentResponses
        List<DocumentResponse> documentResponses = documentClient.findByIds(new HashSet<>(imageIds));

        // 3. Map imageDocId -> url
        Map<String, String> imageIdToUrlMap = documentResponses.stream()
                .collect(Collectors.toMap(DocumentResponse::getId, DocumentResponse::getUrl));

        // 4. Map and replace URLs in images
        return roomTypes.stream()
                .map(hostelRoomType -> {
                    HostelRoomTypeResponse response = mapper.fromHostelRoomType(hostelRoomType);

                    if (response.getHostelRoomImages() != null) {
                        List<HostelRoomImageResponse> updatedImages = response.getHostelRoomImages().stream()
                                .map(imageResponse -> {
                                    String url = imageIdToUrlMap.get(imageResponse.getImageDocId());
                                    return HostelRoomImageResponse.builder()
                                            .id(imageResponse.getId())
                                            .imageDocId(imageResponse.getImageDocId())
                                            .url(url != null ? url : imageResponse.getUrl())
                                            .build();
                                })
                                .toList();
                        response.setHostelRoomImages(updatedImages);
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }


    // Read - Get with pagination
    public Page<HostelRoomTypeResponse> getAllHostelRoomTypes(Pageable pageable) {
      return  repository.findAll(pageable)
                .map(mapper::fromHostelRoomType);
    }

    // Update
    public Optional<HostelRoomTypeResponse> updateHostelRoomType(String id, HostelRoomTypeRequest request) {
        return repository.findById(id)
                .map(existing -> {
                    HostelRoomType hostelRoomType = mapper.toHostelRoomTypeUpdate(existing,request);
                    HostelRoomType updated = repository.save(hostelRoomType);
                    return mapper.fromHostelRoomType(updated);
                });
    }

    // Delete
    public boolean deleteHostelRoomType(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Embedded Operations - Add Image
    public Optional<HostelRoomTypeResponse> addImage(String roomTypeId, List<MultipartFile> imageRequests) {
        List<DocumentResponse>responses=documentClient.saveDocumentList(ObjectListStorageRequest.builder()
                        .bucketName(ObjectUploadPath.Hostel.BASE)
                        .attachedFiles(imageRequests)
                        .objectPath(ObjectUploadPath.Hostel.ROOM_TYPE_IMAGE)
                .build());
        return repository.findById(roomTypeId)
                .map(roomType -> {
                    // Map Set<HostelRoomImageRequest> to Set<HostelRoomImage>
                    Set<HostelRoomImage> newImages = responses.stream()
                            .map(hri->HostelRoomImage.builder()
                                    .imageDocId(hri.getId())
                                    .build())
                            .collect(Collectors.toSet());

                    if (roomType.getHostelRoomImages() == null) {
                        roomType.setHostelRoomImages(new ArrayList<>(newImages));
                    } else {
                        // Ensure no duplicates by converting to Set and back
                        Set<HostelRoomImage> existing = new HashSet<>(roomType.getHostelRoomImages());
                        existing.addAll(newImages);
                        roomType.setHostelRoomImages(new ArrayList<>(existing));
                    }

                    HostelRoomType updated = repository.save(roomType);
                    return mapper.fromHostelRoomType(updated);
                });
    }


    // Embedded Operations - Remove Image
    public Optional<HostelRoomTypeResponse> removeImage(String roomTypeId, String imageId) {
        return repository.findById(roomTypeId)
                .map(roomType -> {
                    if (roomType.getHostelRoomImages() != null) {
                        roomType.getHostelRoomImages().removeIf(img -> img.getId().equals(imageId));
                        HostelRoomType updated = repository.save(roomType);
                        return mapper.fromHostelRoomType(updated);
                    }
                    return mapper.fromHostelRoomType(roomType);
                });
    }

    // Embedded Operations - Add Pricing
//    public Optional<HostelRoomTypeResponse> addPricing(String roomTypeId, HostelRoomTypePricingRequest pricingRequest) {
//        return repository.findById(roomTypeId)
//                .map(roomType -> {
//                    HostelRoomTypePricing newPricing = htPricingMapper.toHostelRoomTypePricing(pricingRequest);
////                    Check whether the request room capacity is not greater than the actual room capacity
//                    validateRoomPricingCapacity(roomType.getMaxCapacity(),newPricing.getCapacity());
//
//                    if (roomType.getHostelRoomPrices() == null) {
//                        roomType.setHostelRoomPrices(List.of(newPricing));
//                    } else {
//                        roomType.getHostelRoomPrices().add(newPricing);
//                    }
//
////                    roomType.setUpdatedAt(LocalDateTime.now());
//                    HostelRoomType updated = repository.save(roomType);
//                    return mapper.fromHostelRoomType(updated);
//                });
//    }

    // Embedded Operations - Update Pricing
    public Optional<HostelRoomTypeResponse> updatePricing(String roomTypeId, String pricingId, HostelRoomTypePricingRequest pricingRequest) {
        return repository.findById(roomTypeId)
                .map(roomType -> {
                    if (roomType.getHostelRoomPrices() != null) {
                        roomType.getHostelRoomPrices().stream()
                                .filter(pricing -> pricing.getId().equals(pricingId))
                                .findFirst()
                                .ifPresent(pricing -> {
                                   htPricingMapper.toHostelRoomTypePricing(pricingRequest);
                                });
                        /*  Check whether the request room capacity is not greater than the actual room capacity*/
                        validateRoomPricingCapacity(roomType.getMaxCapacity(),pricingRequest.getCapacity());
//                        roomType.setUpdatedAt(LocalDateTime.now());
                        HostelRoomType updated = repository.save(roomType);
                        return mapper.fromHostelRoomType(updated);
                    }
                    return mapper.fromHostelRoomType(roomType);
                });
    }

    // Embedded Operations - Remove Pricing
    public Optional<HostelRoomTypeResponse> removePricing(String roomTypeId, String pricingId) {
        return repository.findById(roomTypeId)
                .map(roomType -> {
                    if (roomType.getHostelRoomPrices() != null) {
                        boolean b = roomType.getHostelRoomPrices().removeIf(pricing -> pricing.getId().toString().equals(pricingId));
                        log.info("{}",b);
                        HostelRoomType updated = repository.save(roomType);
                        return mapper.fromHostelRoomType(updated);
                    }
                    return mapper.fromHostelRoomType(roomType);
                });
    }

public Optional<HostelRoomTypeResponse> addPricing(String roomTypeId, Set<HostelRoomTypePricingRequest> pricingRequests) {
    return repository.findById(roomTypeId)
            .map(roomType -> {
                List<HostelRoomTypePricing> existingPrices =
                        roomType.getHostelRoomPrices() == null
                                ? new ArrayList<>()
                                : new ArrayList<>(roomType.getHostelRoomPrices());

                for (HostelRoomTypePricingRequest req : pricingRequests) {
                    // Convert request to entity
                    HostelRoomTypePricing newPricing = htPricingMapper.toHostelRoomTypePricing(req);

                    // Validate capacity
                    validateRoomPricingCapacity(roomType.getMaxCapacity(), newPricing.getCapacity());

                    // Check for existing active pricing with same capacity, clientType, and paymentPeriod
                    Optional<HostelRoomTypePricing> duplicate = existingPrices.stream()
                            .filter(p -> p.getCapacity() == newPricing.getCapacity()
                                    && p.getClientType() == newPricing.getClientType()
                                    && p.getPaymentPeriod() == newPricing.getPaymentPeriod()
                                    && p.getPricingStatus() == PricingStatus.ACTIVE)
                            .findFirst();

                    // If found, deactivate the existing one
                    duplicate.ifPresent(p -> {
//                        TODO Find the HostelRoomTypePricing is found in HostelReservationRoomTypeCount if found make soft delete or make hard delete
                            p.setPricingStatus(PricingStatus.INACTIVE);
                    });

                    // Force the new one to be ACTIVE
                    newPricing.setPricingStatus(PricingStatus.ACTIVE);
                    newPricing.setRoomTypeId(roomTypeId);

                    existingPrices.add(newPricing);
                }

                // Set updated prices
                roomType.setHostelRoomPrices(existingPrices);

                // Save and return updated response
                HostelRoomType updated = repository.save(roomType);
                return mapper.fromHostelRoomType(updated);
            });
}
    private void validateRoomPricingCapacity(int maxCapacity, int requestedCapacity) {
        if (requestedCapacity > maxCapacity) {
            throw new RoomCapacityExceededException(String.format("""
        Room capacity exceeded.

        ⚠️ Please extend the room type capacity first.

        ➤ Maximum allowed capacity: %d
        ➤ Provided capacity: %d

        Hint: Ensure the new room capacity does not exceed the allowed maximum.
        """, maxCapacity, requestedCapacity));
        }
    }

}
