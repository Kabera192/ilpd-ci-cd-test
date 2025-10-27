package rw.ac.ilpd.hostelservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.hostelservice.service.HostelRoomTypeService;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomImageRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypePricingRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypeResponse;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/hostel-room-types")
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Hostel room type controller Endpoints",description = "Apis for managing hostel room type and it associated  images and prices")
public class HostelRoomTypeController {
    private final HostelRoomTypeService service;
    // Create HostelRoomType
    @Operation(summary = "Endpoint for creating hostel room type",description = "Endpoint for creating hostel room type")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HostelRoomTypeResponse> createHostelRoomType(
            @Valid @ModelAttribute HostelRoomTypeRequest request) {
        try {
            HostelRoomTypeResponse response = service.createHostelRoomType(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            if(e instanceof EntityExistsException)
                throw new EntityExistsException(e.getMessage());
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Get hostel types with images and prices by pricing status",
            description = "Returns a list of hostel room types including associated images and prices filtered by the specified pricing status. " +
                    "Each price entry includes its client type (e.g., STUDENT, EXTERNAL). " +
                    "Primarily intended for hostel managers to view available room types and their current pricing based on status."
    )
    @GetMapping("/by-pricing-status")
    public ResponseEntity<List<HostelRoomTypeResponse>> getAllHostelRoomTypesByPricingStatus(
            @RequestParam(name = "pricing-status", defaultValue = "ACTIVE") String pricingStatus) {

        List<HostelRoomTypeResponse> responses = service.getAllHostelRoomTypesHavingSpecifiedPricingStatus(pricingStatus);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Get hostel types with images and prices by pricing status and client type",
            description = "Returns a list of hostel room types including associated images and prices filtered by both pricing status and client type. " +
                    "Useful for hostel managers who need to view pricing information tailored to a specific category of clients such as STUDENT or EXTERNAL."
    )
    @GetMapping("/by-pricing-status-and-client-type")
    public ResponseEntity<List<HostelRoomTypeResponse>> getAllHostelRoomTypesWithPricingStatusAndClientType(
            @RequestParam(name = "pricing-status", defaultValue = "ACTIVE") String pricingStatus,
            @RequestParam(name = "client-type", defaultValue = "STUDENT") String clientType) {

        List<HostelRoomTypeResponse> responses = service.getAllHostelRoomTypesWithHavingPricingStatusAndClientType(pricingStatus, clientType);
        return ResponseEntity.ok(responses);
    }
    // Get all HostelRoomTypes
//    @Operation(summary = "Endpoint highlighting the details of hostel type and their images and the active prices each type has with it corresponding type of client (Mainly for Hostel Manager)")
//    @GetMapping
////    this is for Hoster manager
//    public ResponseEntity<List<HostelRoomTypeResponse>> getAllHostelRoomTypes() {
//        List<HostelRoomTypeResponse> responses = service.getAllHostelRoomTypesWithTheirPricingStatus(PricingStatus.ACTIVE);
//        return new ResponseEntity<>(responses, HttpStatus.OK);
//    }
//    @GetMapping
//    this is for Hoster manager
//    public ResponseEntity<List<HostelRoomTypeResponse>> getAllHostelRoomTypesForClient() {
//        List<HostelRoomTypeResponse> responses = service.getAllHostelRoomTypesWithTheirPricingStatus(PricingStatus.ACTIVE);
//        return new ResponseEntity<>(responses, HttpStatus.OK);
//    }
    // Get all HostelRoomTypes with pagination
@Operation(summary = "Paginated endpoint for hostel room type")
@GetMapping("/paginated")
    public ResponseEntity<Page<HostelRoomTypeResponse>> getAllHostelRoomTypesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<HostelRoomTypeResponse> responses = service.getAllHostelRoomTypes(pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Get HostelRoomType by ID
    @Operation(summary = "Find hostel room type by id")
    @GetMapping("/{id}")
    public ResponseEntity<HostelRoomTypeResponse> getHostelRoomTypeById(@PathVariable String id) {
        return service.getHostelRoomTypeById(id)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update HostelRoomType
    @Operation(summary = "Update hostel room type ")
    @PutMapping("/{id}")
    public ResponseEntity<HostelRoomTypeResponse> updateHostelRoomType(
            @PathVariable String id,
            @Valid @RequestBody HostelRoomTypeRequest request) {
        return service.updateHostelRoomType(id, request)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete HostelRoomType
    @Operation(summary = "Delete hostel room type by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHostelRoomType(@PathVariable String id) {
        if (service.deleteHostelRoomType(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Embedded Operations - Images

    // Add Image to HostelRoomType
    @Operation(summary = "Add hostel room type image")
    @PostMapping(path = "/{roomTypeId}/images",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HostelRoomTypeResponse> addImages(
            @PathVariable String roomTypeId,
            @Valid @ModelAttribute List<MultipartFile> images) {
        return service.addImage(roomTypeId, images)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Remove Image from HostelRoomType
    @Operation(summary = "Remove Image from HostelRoomType")
    @DeleteMapping("/{roomTypeId}/images/{imageId}")
    public ResponseEntity<HostelRoomTypeResponse> removeImage(
            @PathVariable String roomTypeId,
            @PathVariable String imageId) {
        return service.removeImage(roomTypeId, imageId)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Embedded Operations - Pricing

    // Add Pricing to HostelRoomType
    @Operation(summary = "Add Pricing to HostelRoomType")
//    @PostMapping("/{roomTypeId}/pricing")
//    public ResponseEntity<HostelRoomTypeResponse> addPricing(
//            @PathVariable String roomTypeId,
//            @Valid @RequestBody HostelRoomTypePricingRequest pricingRequest) {
//        return service.addPricing(roomTypeId, pricingRequest)
//                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
    @PostMapping("/{roomTypeId}/pricing")
    public ResponseEntity<?> addPricing(
            @PathVariable String roomTypeId,
            @Valid @RequestBody Set<HostelRoomTypePricingRequest> pricingRequests) {
        return service.addPricing(roomTypeId, pricingRequests)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Update Pricing in HostelRoomType
    @Operation(summary = "Update Pricing in HostelRoomType")
    @PutMapping("/{roomTypeId}/pricing/{pricingId}")
    public ResponseEntity<HostelRoomTypeResponse> updatePricing(
            @PathVariable String roomTypeId,
            @PathVariable String pricingId,
            @Valid @RequestBody HostelRoomTypePricingRequest pricingRequest) {
        return service.updatePricing(roomTypeId, pricingId, pricingRequest)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Remove Pricing from HostelRoomType
    @Operation(summary = "Remove Pricing from HostelRoomType")
    @DeleteMapping("/{roomTypeId}/pricing/{pricingId}")
    public ResponseEntity<HostelRoomTypeResponse> removePricing(
            @PathVariable String roomTypeId,
            @PathVariable String pricingId) {
        return service.removePricing(roomTypeId, pricingId)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}