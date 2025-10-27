package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.LecturerService;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerApi;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerUserResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Controller for managing Lecturer endpoints.
 */
@RestController
@RequestMapping("/lecturers")
@RequiredArgsConstructor
@Tag(name = "Lecture Apis", description = "APIs for managing Lectures (Author: Michael)")
@Slf4j
public class LectureController implements LecturerApi {

    private final LecturerService lecturerService;

    @Override
    public ResponseEntity<LecturerResponse> createLecturer(LecturerRequest request) {
        log.info("Creating Lecturer having user id :: {}", request.getUserId());
        LecturerResponse response= lecturerService.createLecturer(request);
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<LecturerResponse> updateLecturerDetails(String id, LecturerRequest lecturerRequest) {
       LecturerResponse response=  lecturerService.updateLecturer(id, lecturerRequest);
       return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> updateLecturerStatus(String id, String activeStatus) {
        String result=lecturerService.updateLecturerStatus(id,activeStatus);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> restoreLecturer(String id, LocalDate extendPeriod) {
        String response=lecturerService.restoreLecturerArchivedLecturer(id,extendPeriod);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> updateEngagementType(String id, String engagementType) {
        String response=lecturerService.updateEngagementType(id,engagementType);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> extendContract(String id, LocalDate newEndDate) {
        String response=lecturerService.extendContract(id,newEndDate);
        return ResponseEntity.ok(response);
    }
//delete lecturer
    @Override
    public ResponseEntity<String> deactivateLecturer(String id) {
        String response=lecturerService.deactivateLecturer(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PagedResponse<LecturerResponse>> getLecturers(int page, int size, String sortBy, String search, String display, String orderBy) {
        PagedResponse<LecturerResponse> response=lecturerService.getListOfPagedLecturerUserDetail(page, size, display,sortBy, search, orderBy);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LecturerResponse> getLecturerById(String id) {
        LecturerResponse response= lecturerService.findLecturerById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LecturerResponse>> getLecturersByIds(Set<String> ids) {
        List<LecturerResponse>responses=lecturerService.getLecturersByIds(ids);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<LecturerResponse>> getAllLecturers(String search, String display) {
        List<LecturerResponse>response=lecturerService.getAllLecturers(search,display);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LecturerResponse>> getLecturersCreatedAfter(LocalDateTime fromDate) {
        return null;
    }

//    @Override
//    public ResponseEntity<PagedResponse<LecturerUserResponse>> getLecturers(int page, int size, String sortBy, String search, String display, String orderBy) {
//        PagedResponse<LecturerUserResponse> response=lecturerService.getListOfPagedLecturerUserDetail(page, size, display,sortBy, search, orderBy);
//        return ResponseEntity.ok(response);
//    }
//
//    @Override
//    public ResponseEntity<LecturerUserResponse> getLecturerById(String id) {
//        LecturerUserResponse response= lecturerService.findLecturerById(id);
//        return ResponseEntity.ok(response);
//    }
//
//    @Override
//    public ResponseEntity<List<LecturerUserResponse>> getLecturersByIds(Set<String> ids) {
//        List<LecturerUserResponse>responses=lecturerService.getLecturersByIds(ids);
//        return ResponseEntity.ok(responses);
//    }
////TODO
//    @Override
//    public ResponseEntity<List<LecturerUserResponse>> getAllLecturers(String search, String display) {
//        List<LecturerUserResponse>response=lecturerService.getAllLecturers(search,display);
//        return ResponseEntity.ok(response);
//    }
////TODO
//    @Override
//    public ResponseEntity<List<LecturerUserResponse>> getLecturersCreatedAfter(LocalDateTime fromDate) {
//         return null;
//    }
//TODO
    @Override
    public ResponseEntity<Long> getLecturersCount(String fromDate, String toDate, String activeStatus, String engagementStatus) {
        return null;
    }
//    /**
//     * Creates a new Lecturer.
//     * @param id Lecturer ID
//     * @return ResponseEntity with the found Lecturer
//     * @throws IllegalArgumentException if ID is invalid
//     * @throws IllegalStateException if Lecturer is already active
//     */
//    @Operation(
//            summary = "Find Lecturer by ID",
//            description = "Retrieves a lecturer by their ID."
//    )
//    /**
//     * Retrieves all lecturers with pagination and search.
//     *
//     * @param page Page number
//     * @param size Page size
//     * @param sortBy Sort field
//     * @param search Search keyword (applied to user info)
//     * @param display Display filter (e.g., "active")
//     * @param orderBy Order type ("asc" or "desc")
//     * @return ResponseEntity with paged LecturerUserResponse
//     */
//    @Operation(
//        summary = "Get all lecturers",
//        description = "Retrieves all lecturers with pagination and search"
//    )
//    @GetMapping
//    public ResponseEntity<PagedResponse<LecturerUserResponse>> getAll(
//             @RequestParam(value = "page", defaultValue = "0") int page,
//             @RequestParam(value = "size", defaultValue = "10") int size,
//             @RequestParam(value = "sort-by", defaultValue = "createdAt") String sortBy,
//             @RequestParam(value = "search", defaultValue = "", required = false) String search,
//            @RequestParam(value = "display", defaultValue = "active") String display,
//             @RequestParam(value = "order-by", defaultValue = "asc") String orderBy
//    ) {
//        return lecturerService.getListOfPagedLecturerUserDetail(page, size, display,sortBy, search, orderBy);
//    }
//
//    /**
//     * Soft deletes (deactivates) a lecturer by setting status to INACTIVE and end date to now.
//     *
//     * @param id Lecturer ID
//     * @return ResponseEntity with status message
//     */
//    @Operation(
//        summary = "Delete Lecturer",
//        description = "Soft deletes (deactivates) a lecturer by setting status to INACTIVE and end date to now"
//    )
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLecturer(
//            @Parameter(description = "Lecturer ID") @PathVariable String id
//    ) {
//        return lecturerService.deleteLecturer(id);
//    }

//    /**
//     * Restores a previously deleted (inactive) lecturer by setting status to ACTIVE and optionally extending end date.
//     *
//     * @param id Lecturer ID
//     * @param extendPeriod Optional new end date to set
//     * @return ResponseEntity with status message
//     */
//    @Operation(
//        summary = "Undo Lecturer Delete",
//        description = "Restores a previously deleted (inactive) lecturer by setting status to ACTIVE and optionally extending end date."
//    )
//    @PatchMapping("/delete/undo/{id}")
//    public ResponseEntity<String> undoLecturerDelete(
//            @Parameter(description = "Lecturer ID") @PathVariable String id,
//            @Parameter(description = "Optional new end date") @RequestParam(required = false) LocalDate extendPeriod
//    ) {
//        return lecturerService.undoLecturerDelete(id, extendPeriod);
//    }
}
