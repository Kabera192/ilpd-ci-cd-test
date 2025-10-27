package rw.ac.ilpd.hostelservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.hostelservice.service.HostelReservationService;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountRequest;
import rw.ac.ilpd.sharedlibrary.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hostel-reservations")
@CrossOrigin(origins = "*")
@Tag(name = "Hostel Reservations", description = "CRUD operations for hostel reservations management")
@Slf4j
public class HostelReservationController {

    @Autowired
    private HostelReservationService service;

    @PostMapping
    @Operation(
            summary = "Create a new hostel reservation",
            description = "Creates a new hostel reservation with room type counts and generates a random query code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Hostel reservation created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> create(
            @Parameter(description = "Hostel reservation data", required = true)
            @Valid @RequestBody HostelReservationRequest request) {
        log.info("Creating new hostel reservation for reserver: {}", request.getReservationStatus());
        try {
            HostelReservationResponse response = service.create(request);
            log.info("Successfully created hostel reservation with ID: {} and query code: {}",
                    response.getId(), response.getRandomQueryCode());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating hostel reservation for reserver: {}", request.getReservationType(), e);
            throw e;
        }
    }

    @GetMapping
    @Operation(
            summary = "Get all hostel reservations",
            description = "Retrieves a list of all hostel reservations in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all hostel reservations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HostelReservationResponse>> findAll() {
        log.info("Retrieving all hostel reservations");
        try {
            List<HostelReservationResponse> responses = service.findAll();
            log.info("Successfully retrieved {} hostel reservations", responses.size());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error retrieving all hostel reservations", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get hostel reservation by ID",
            description = "Retrieves a specific hostel reservation by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hostel reservation found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Hostel reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> findById(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String id) {
        log.info("Retrieving hostel reservation with ID: {}", id);
        try {
            return service.findById(id)
                    .map(response -> {
                        log.info("Successfully found hostel reservation with ID: {}", id);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation not found with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error retrieving hostel reservation with ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping("/query-code/{queryCode}")
    @Operation(
            summary = "Get hostel reservation by query code",
            description = "Retrieves a hostel reservation using its randomly generated query code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hostel reservation found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Hostel reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> findByQueryCode(
            @Parameter(description = "Random query code", required = true, example = "AB12CD34")
            @PathVariable String queryCode) {
        log.info("Retrieving hostel reservation with query code: {}", queryCode);
        try {
            return service.findByRandomQueryCode(queryCode)
                    .map(response -> {
                        log.info("Successfully found hostel reservation with query code: {}", queryCode);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation not found with query code: {}", queryCode);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error retrieving hostel reservation with query code: {}", queryCode, e);
            throw e;
        }
    }

    @GetMapping("/reserver/{reserverPersonId}")
    @Operation(
            summary = "Get hostel reservations by reserver person ID",
            description = "Retrieves all hostel reservations made by a specific person"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved hostel reservations for the reserver",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HostelReservationResponse>> findByReserverPersonId(
            @Parameter(description = "Reserver person ID", required = true, example = "user-123")
            @PathVariable String reserverPersonId) {
        log.info("Retrieving hostel reservations for reserver: {}", reserverPersonId);
        try {
            List<HostelReservationResponse> responses = service.findByReserverPersonId(reserverPersonId);
            log.info("Successfully retrieved {} hostel reservations for reserver: {}",
                    responses.size(), reserverPersonId);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error retrieving hostel reservations for reserver: {}", reserverPersonId, e);
            throw e;
        }
    }

    @GetMapping("/status")
    @Operation(
            summary = "Get hostel reservations by status",
            description = "Retrieves all hostel reservations with a specific reservation status"
    )
    public ResponseEntity<List<HostelReservationResponse>> findByStatus(
            @Parameter(description = "Reservation status", required = true, example = "ONGOING")
            @RequestParam String status) {
        log.info("Retrieving hostel reservations with status: {}", status);
        try {
            List<HostelReservationResponse> responses = service.findByStatus(ReservationStatus.valueOf(status));
            log.info("Successfully retrieved {} hostel reservations with status: {}",
                    responses.size(), status);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error retrieving hostel reservations with status: {}", status, e);
            throw e;
        }
    }

    @GetMapping("/arrival-date-range")
    @Operation(
            summary = "Get hostel reservations by arrival date range",
            description = "Retrieves all hostel reservations with arrival dates within the specified range"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved hostel reservations by date range",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid date format or range"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<HostelReservationResponse>> findByArrivalDateRange(
            @Parameter(description = "Start date (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2024-01-01T00:00:00")
            @RequestParam LocalDateTime startDate,
            @Parameter(description = "End date (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2024-12-31T23:59:59")
            @RequestParam LocalDateTime endDate) {
        log.info("Retrieving hostel reservations with arrival dates between {} and {}", startDate, endDate);
        try {
            List<HostelReservationResponse> responses = service.findByArrivalDateRange(startDate, endDate);
            log.info("Successfully retrieved {} hostel reservations with arrival dates between {} and {}",
                    responses.size(), startDate, endDate);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error retrieving hostel reservations with arrival dates between {} and {}",
                    startDate, endDate, e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a hostel reservation",
            description = "Updates an existing hostel reservation with new data"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hostel reservation updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Hostel reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> update(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String id,
            @Parameter(description = "Updated hostel reservation data", required = true)
            @Valid @RequestBody  HostelReservationRequest request) {
        log.info("Updating hostel reservation with ID: {}", id);
        try {
            return service.update(id, request)
                    .map(response -> {
                        log.info("Successfully updated hostel reservation with ID: {}", id);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation not found for update with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error updating hostel reservation with ID: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a hostel reservation",
            description = "Permanently deletes a hostel reservation from the system"
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String id) {
        log.info("Deleting hostel reservation with ID: {}", id);
        try {
            boolean deleted = service.delete(id);
            if (deleted) {
                log.info("Successfully deleted hostel reservation with ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                log.warn("Hostel reservation not found for deletion with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error deleting hostel reservation with ID: {}", id, e);
            throw e;
        }
    }

    @PostMapping("/{reservationId}/room-type-counts")
    @Operation(
            summary = "Add room type count to hostel reservation",
            description = "Adds a new room type count to an existing hostel reservation"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Room type count added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Hostel reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> addRoomTypeCount(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String reservationId,
            @Parameter(description = "Room type count data", required = true)
            @Valid @RequestBody HostelReservationRoomTypeCountRequest request) {
        log.info("Adding room type count to hostel reservation with ID: {}", reservationId);
        try {
            return service.addRoomTypeCount(reservationId, request)
                    .map(response -> {
                        log.info("Successfully added room type count to hostel reservation with ID: {}", reservationId);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation not found for adding room type count with ID: {}", reservationId);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error adding room type count to hostel reservation with ID: {}", reservationId, e);
            throw e;
        }
    }

    @PutMapping("/{reservationId}/room-type-counts/{roomTypeCountId}")
    @Operation(
            summary = "Update room type count in hostel reservation",
            description = "Updates a specific room type count within a hostel reservation"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Room type count updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Hostel reservation or room type count not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> updateRoomTypeCount(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String reservationId,
            @Parameter(description = "Room type count ID", required = true, example = "rtc-123")
            @PathVariable String roomTypeCountId,
            @Parameter(description = "Updated room type count data", required = true)
            @Valid @RequestBody HostelReservationRoomTypeCountRequest request) {
        log.info("Updating room type count with ID: {} in hostel reservation with ID: {}", roomTypeCountId, reservationId);
        try {
            return service.updateRoomTypeCount(reservationId, roomTypeCountId, request)
                    .map(response -> {
                        log.info("Successfully updated room type count with ID: {} in hostel reservation with ID: {}",
                                roomTypeCountId, reservationId);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation or room type count not found for update. Reservation ID: {}, Room type count ID: {}",
                                reservationId, roomTypeCountId);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error updating room type count with ID: {} in hostel reservation with ID: {}",
                    roomTypeCountId, reservationId, e);
            throw e;
        }
    }

    @DeleteMapping("/{reservationId}/room-type-counts/{roomTypeCountId}")
    @Operation(
            summary = "Remove room type count from hostel reservation",
            description = "Removes a specific room type count from a hostel reservation"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Room type count removed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HostelReservationResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Hostel reservation or room type count not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HostelReservationResponse> removeRoomTypeCount(
            @Parameter(description = "Hostel reservation ID", required = true, example = "64f1a2b3c4d5e6f7a8b9c0d1")
            @PathVariable String reservationId,
            @Parameter(description = "Room type count ID", required = true, example = "rtc-123")
            @PathVariable String roomTypeCountId) {
        log.info("Removing room type count with ID: {} from hostel reservation with ID: {}", roomTypeCountId, reservationId);
        try {
            return service.removeRoomTypeCount(reservationId, roomTypeCountId)
                    .map(response -> {
                        log.info("Successfully removed room type count with ID: {} from hostel reservation with ID: {}",
                                roomTypeCountId, reservationId);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("Hostel reservation not found for removing room type count. Reservation ID: {}, Room type count ID: {}",
                                reservationId, roomTypeCountId);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error removing room type count with ID: {} from hostel reservation with ID: {}",
                    roomTypeCountId, reservationId, e);
            throw e;
        }
    }
}