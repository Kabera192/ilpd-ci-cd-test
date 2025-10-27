package rw.ac.ilpd.sharedlibrary.dto.lecturer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface LecturerApi {
    /**
     * Creates a new lecturer.
     *
     * @param request DTO containing lecturer details
     * @return ResponseEntity containing the created LecturerResponse
     */
    @PostMapping
    public ResponseEntity<LecturerResponse> createLecturer(
            @RequestBody @Valid LecturerRequest request
    );

    /**
     * Updates an existing lecturer's details.
     *
     * @param id Lecturer identifier (UUID)
     * @param lecturerRequest DTO containing updated lecturer information
     * @return ResponseEntity containing the updated LecturerUserResponse
     */
    @PutMapping("/{id}")
    ResponseEntity<LecturerResponse> updateLecturerDetails(
            @PathVariable
            @NotBlank(message = "Lecturer ID is required for update")
            String id,
            @Valid @RequestBody LecturerRequest lecturerRequest
    );

    /**
     * Updates the employment status of a lecturer.
     *
     * @param id Lecturer identifier (UUID)
     * @param activeStatus New employment status (ACTIVE, INACTIVE, SUSPENDED, CONTRACT_TERMINATED)
     * @return ResponseEntity with operation status message
     */
    @PatchMapping("/status/{id}")
    ResponseEntity<String> updateLecturerStatus(
            @PathVariable
            @NotBlank(message = "Lecturer ID is required to update status")
            @ValidUuid(message = "Invalid lecturer ID format")
            String id,
            @RequestParam(name = "employmentActiveStatus")
            @NotBlank(message = "Employment status is required")
            @Pattern(
                    regexp = "^(ACTIVE|INACTIVE|SUSPENDED|CONTRACT_TERMINATED)$",
                    message = "Invalid status. Allowed values: ACTIVE, INACTIVE, SUSPENDED, CONTRACT_TERMINATED"
            ) String activeStatus
    );

    /**
     * Restores a previously deactivated lecturer by setting status to ACTIVE
     * and optionally extending the contract end date.
     *
     * @param id Lecturer identifier (UUID)
     * @param extendPeriod Optional new contract end date
     * @return ResponseEntity with operation status message
     */
    @PatchMapping("/restore/{id}")
    public ResponseEntity<String> restoreLecturer(
            @PathVariable
            @NotBlank(message = "Lecturer ID is required to restore")
            @ValidUuid(message = "Invalid lecturer identifier format")
            String id,
            @RequestParam(required = false) LocalDate extendPeriod
    );

    /**
     * Updates the engagement type of a lecturer (e.g., PERMANENT, CONTRACT, PART_TIME).
     *
     * @param id Lecturer identifier (UUID)
     * @param engagementType New engagement type to assign
     * @return ResponseEntity with operation status message
     */
    @PatchMapping("/{id}/engagement-type")
    ResponseEntity<String> updateEngagementType(
            @PathVariable String id,
            @RequestParam String engagementType
    );

    /**
     * Extends the contract of a lecturer by updating the end date.
     *
     * @param id Lecturer identifier (UUID)
     * @param newEndDate New contract end date
     * @return ResponseEntity with operation status message
     */
    @PatchMapping("/{id}/extend-contract")
    ResponseEntity<String> extendContract(
            @PathVariable String id,
            @RequestParam LocalDate newEndDate
    );

    /**
     * Soft deletes (deactivates) a lecturer by setting status to INACTIVE
     * and end date to the current date.
     *
     * @param id Lecturer identifier (UUID)
     * @return ResponseEntity with operation status message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateLecturer(
            @PathVariable
            @NotBlank(message = "Lecturer ID is required for deactivation")
            @ValidUuid(
                    message = "Invalid lecturer ID format"
            ) String id
    );

    /**
     * Retrieves a paginated list of lecturers with optional filtering and sorting.
     *
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @param sortBy Field to sort by (default: createdAt)
     * @param search Search keyword applied to lecturer details
     * @param display Display filter (e.g., "active")
     * @param orderBy Sorting order ("asc" or "desc")
     * @return ResponseEntity containing a paged response of LecturerUserResponse
     */
    @GetMapping
    ResponseEntity<PagedResponse<LecturerResponse>> getLecturers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @RequestParam(value = "display", defaultValue = "active") String display,
            @RequestParam(value = "order-by", defaultValue = "asc") String orderBy
    );

    /**
     * Retrieves a lecturer by ID.
     *
     * @param id Lecturer identifier (UUID)
     * @return ResponseEntity containing LecturerResponse
     */
    @GetMapping("/{id}")
    ResponseEntity<LecturerResponse> getLecturerById(
            @PathVariable String id
    );

    /**
     * Retrieves a list of lecturers by their IDs.
     *
     * @param ids Set of lecturer identifiers (UUIDs)
     * @return ResponseEntity containing a list of LecturerResponse
     */
    @PostMapping("/by-ids")
    ResponseEntity<List<LecturerResponse>> getLecturersByIds(
            @RequestBody Set<String> ids
    );

    /**
     * Retrieves all lecturers with optional search and display filters.
     *
     * @param search Optional search keyword
     * @param display Display filter (e.g., "active")
     * @return ResponseEntity containing a list of LecturerResponse
     */
    @GetMapping("/all")
    ResponseEntity<List<LecturerResponse>> getAllLecturers(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(name = "display", defaultValue = "active") String display
    );

    /**
     * Retrieves all lecturers created after the specified date.
     *
     * @param fromDate The starting date for filtering lecturers
     * @return ResponseEntity containing a list of LecturerResponse
     */
    @GetMapping("/created-after")
    ResponseEntity<List<LecturerResponse>> getLecturersCreatedAfter(
            @RequestParam LocalDateTime fromDate
    );
    @GetMapping("/count")
    ResponseEntity<Long> getLecturersCount(
            @RequestParam(defaultValue = "",required = false) String fromDate,
            @RequestParam(defaultValue = "",required = false) String toDate,
            @RequestParam( name = "display", required = false,defaultValue = "ACTIVE")
            @Pattern(
                    regexp = "^(ACTIVE|INACTIVE|SUSPENDED|CONTRACT_TERMINATED)$",
                    message = "Invalid status. Allowed values: ACTIVE, INACTIVE, SUSPENDED, CONTRACT_TERMINATED"
            ) String activeStatus,
            @RequestParam(name = "engagementStatus",defaultValue = "NONE")
            @Pattern(
                    regexp = "^(TEMPORARY|PERMANENT|NONE)$",
                    message = "Invalid status. Allowed values: ACTIVE, INACTIVE, SUSPENDED, CONTRACT_TERMINATED"
            )String engagementStatus
    );
}
