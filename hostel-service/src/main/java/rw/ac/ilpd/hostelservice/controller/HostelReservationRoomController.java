package rw.ac.ilpd.hostelservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.hostelservice.service.HostelReservationRoomService;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomUpdateRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hostel-reservation-rooms")
@Validated
@Slf4j
public class HostelReservationRoomController {

    private final HostelReservationRoomService service;

    public HostelReservationRoomController(HostelReservationRoomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HostelReservationRoomResponse> create(
            @Valid @RequestBody HostelReservationRoomRequest request) {

        HostelReservationRoomResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelReservationRoomResponse> findById(@PathVariable String id) {
        HostelReservationRoomResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservation-code/{reservationCode}")
    public ResponseEntity<HostelReservationRoomResponse> findByReservationCode(
            @PathVariable String reservationCode) {

        HostelReservationRoomResponse response = service.findByReservationCode(reservationCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HostelReservationRoomResponse>> findAll() {
        List<HostelReservationRoomResponse> responses = service.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<HostelReservationRoomResponse>> findByReservationId(
            @PathVariable UUID reservationId) {

        List<HostelReservationRoomResponse> responses = service.findByReservationId(reservationId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<HostelReservationRoomResponse>> findByClientId(
            @PathVariable UUID clientId) {

        List<HostelReservationRoomResponse> responses = service.findByClientId(clientId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HostelReservationRoomResponse> update(
            @PathVariable String id,
            @Valid @RequestBody HostelReservationRoomUpdateRequest request) {

        HostelReservationRoomResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}