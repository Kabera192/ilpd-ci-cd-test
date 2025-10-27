package rw.ac.ilpd.sharedlibrary.dto.position;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.util.List;

@RequestMapping("/api/v1/positions")
public interface PositionApi {

    /**
     * Create a new position
     * POST /api/v1/positions
     */
    @PostMapping
    ResponseEntity<PositionResponse> createPosition(@Valid @RequestBody PositionRequest request);

    /**
     * Get all positions
     * GET /api/v1/positions
     */
    @GetMapping
    ResponseEntity<List<PositionResponse>> getAllPositions(@RequestParam(required = false,defaultValue = "") String search);

    /**
     * Get positions with pagination
     * GET /api/v1/positions/paginated?page=0&size=10&sort=name,asc
     */
    @GetMapping("/paginated")
    ResponseEntity<PagedResponse<PositionResponse>> getPositionsWithPagination(
            @RequestParam(name = "search", required = false,defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection);

    /**
     * Get position by ID
     * GET /api/v1/positions/{id}
     */
    @GetMapping("/{id}")
    ResponseEntity<PositionResponse> getPositionById(@PathVariable String id);

    /**
     * Update position
     * PUT /api/v1/positions/{id}
     */
    @PutMapping("/{id}")
    ResponseEntity<PositionResponse> updatePosition(
            @PathVariable String id,
            @Valid @RequestBody PositionRequest request);

    /**
     * Delete position
     * DELETE /api/v1/positions/{id}
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePosition(@PathVariable String id);
    /**
     * Restore archives position
     * DELETE /api/v1/positions/{id}
     */
    ResponseEntity<PositionResponse> restoreArchive(String id);

}
