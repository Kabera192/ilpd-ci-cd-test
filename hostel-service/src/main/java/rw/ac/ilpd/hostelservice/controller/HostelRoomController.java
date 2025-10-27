package rw.ac.ilpd.hostelservice.controller;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.hostelservice.service.HostelRoomService;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomResponse;

import java.util.List;

@RestController
@RequestMapping("/api/hostel-rooms")
public class HostelRoomController {

    private final HostelRoomService service;

    public HostelRoomController(HostelRoomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HostelRoomResponse> create(@Valid @RequestBody HostelRoomRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<HostelRoomResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelRoomResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HostelRoomResponse> update(@PathVariable String id, @Valid @RequestBody HostelRoomRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
