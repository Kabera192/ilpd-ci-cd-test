package rw.ac.ilpd.academicservice.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.integration.client.UserClient;
import rw.ac.ilpd.academicservice.mapper.LecturerMapper;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.repository.sql.LecturerRepository;
import rw.ac.ilpd.mis.shared.util.errors.InvalidInputException;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerUserResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;
import rw.ac.ilpd.sharedlibrary.enums.EngagementType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LecturerService {
    private final LecturerRepository lecturerRepository;
private final LecturerMapper lecturerMapper;
private final UserClient userClient;

    /**
     * Creates a new Lecturer after verifying the user exists.
     *
     * @param lecturer LecturerRequest DTO
     * @return ResponseEntity with created Lecturer
     */
    @Transactional(readOnly = false)
    public LecturerResponse createLecturer(@Valid LecturerRequest lecturer) {
        // Check if user exists in user service
        ResponseEntity<UserResponse> userResponse = userClient.getUserById(lecturer.getUserId());
        if (userResponse == null) {
            log.error("Create Lecturer failed: User not found");
            throw new EntityNotFoundException("Lecturer not registered");
        }
        // Check if an active lecturer already exists for this user
        assert userResponse.getBody() != null;
//        find lecturer if exist in the table
        Optional<Lecturer> activeLecturer = findLastLecturerByUserId(
                lecturer.getUserId()).stream()
                .findFirst();

        activeLecturer.ifPresent(lecturer1 -> {
            throw new EntityAlreadyExists("Lecturer already exists");
        });

        // Save new lecturer
       try {
           Lecturer savedLecturer = lecturerRepository.save(lecturerMapper.toLecturer(lecturer));
           return lecturerMapper.fromLecturer(savedLecturer);
       }
       catch (Exception e) {
           if(e instanceof DataIntegrityViolationException) {
               throw new EntityAlreadyExists("Lecturer having this id already exists");
           }
           throw new EntityAlreadyExists("Lecturer having this id already exists");

       }
    }
    @Transactional(readOnly = true)
    public Optional<Lecturer> getEntity(String id){
	// This is a comment to test the CI/CD pipeline!
        return lecturerRepository.findById(UUID.fromString(id));
    }

    @Transactional
//    public LecturerUserResponse findLecturerById(String id){
    public LecturerResponse findLecturerById(String id){
        Lecturer lecturer = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
//        ResponseEntity<UserResponse> userResponse = userClient.getUserById(lecturer.getUserId().toString());
//       try {
//           UserResponse userResponseEntity = new UserResponse();
//           if(userResponse.getStatusCode().is2xxSuccessful()){
//               userResponseEntity=userResponse.getBody();
//           }
//           return new LecturerUserResponse(lecturerMapper.fromLecturer(lecturer), userResponseEntity);
//       }catch (Exception e){
//           log.info(" Error :: {}",e);
//           return  null;
//        }
        return lecturerMapper.fromLecturer(lecturer);
    }
//    public  Optional<Lecturer>findLecturerByUserIdAndActiveStatus(UUID userId, EmploymentStatus activeStatus){
//    return lecturerRepository.findByUserIdAndActiveStatus(userId,activeStatus);
//    }
@Transactional
public  Optional<Lecturer>findLastLecturerByUserId(String user){
        return lecturerRepository.findFirstByUserIdOrderByCreatedAtDesc(UUID.fromString(user));
    }
    /**
     * Retrieves a paged list of lecturers, searching by user info.
     *
     * @param page Page number
     * @param size Page size
     * @param sort Sort field
     * @param search Search keyword (applied to user info)
     * @param orderBy Sort order ("asc" or "desc")
     * @return ResponseEntity containing paged LecturerUserResponse
     */
    @Transactional
//    public PagedResponse<LecturerUserResponse> getListOfPagedLecturerUserDetail(
    public PagedResponse<LecturerResponse> getListOfPagedLecturerUserDetail(
            int page,
            int size,
//            TODO remember to add the use of this functionality
            String display,
            String sort,
            String search,
            String orderBy
    ) {
        // Build pageable object with sorting
        Pageable pageable = PageRequest.of(
                page,
                size,
                orderBy.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending()
        );

        // Fetch lecturers with optional search
        Page<Lecturer> lecturerPage;
//        if (search == null || search.isBlank()) {
            lecturerPage = lecturerRepository.findAll(pageable);
//        } else {
            // 1. Search users by keyword using Feign client
//            List<UserResponse> matchedUsers = userClient.searchUsers(search); // You need this endpoint in user service
//
//            // 2. Get user IDs from matched users
//            List<UUID> userIds = matchedUsers.stream()
//                    .map(user -> UUID.fromString(user.getId()))
//                    .toList();

            // 3. Fetch lecturers whose userId is in userIds, paged
//            lecturerPage = lecturerRepository.findByUserIdIn(userIds, pageable);
//        }

        // 4. Map lecturers to LecturerUserResponse, fetching user info via Feign client
//        List<LecturerUserResponse> content = lecturerPage.getContent().stream()
//                .map(lecturer -> {
//                    ResponseEntity<UserResponse> userResponse = userClient.getUserById(lecturer.getUserId().toString());
//                    return lecturerMapper.toLecturerUserResponse(lecturerMapper.fromLecturer(lecturer), userResponse.getBody());
//                })
//                .toList();

        // 5. Build paged response

        return new PagedResponse<>(
//                content,
                lecturerPage.getContent().stream().map(lecturerMapper::fromLecturer).toList(),
                lecturerPage.getNumber(),
                lecturerPage.getSize(),
                lecturerPage.getTotalElements(),
                lecturerPage.getTotalPages(),
                lecturerPage.isLast()
        );
    }


    public List<LecturerUserResponse> getListOfLecturerUserDetail(
            String display,
            String search
    ) {
        // 1. Get all lecturers from DB (optionally filter by status)
        List<Lecturer> lecturers = lecturerRepository.findAll();

        // 2. Filter lecturers by display (active/inactive/all)
        List<Lecturer> filteredLecturers;
        if ("active".equalsIgnoreCase(display)) {
            filteredLecturers = lecturers.stream()
                    .filter(l -> l.getActiveStatus() == EmploymentStatus.ACTIVE)
                    .toList();
        } else if ("inactive".equalsIgnoreCase(display)) {
            filteredLecturers = lecturers.stream()
                    .filter(l -> l.getActiveStatus() == EmploymentStatus.INACTIVE)
                    .toList();
        } else {
            filteredLecturers = lecturers;
        }

        // 3. Collect userIds from filtered lecturers
        List<UUID> userIds = filteredLecturers.stream()
                .map(Lecturer::getUserId)
                .toList();

        // 4. Fetch user info for those userIds from user service
        List<UserResponse> users = userClient.getUsersByIds(userIds);

        // 5. If search is provided, filter users by search keyword (e.g., name, email, etc.)
        List<UserResponse> filteredUsers;
        if (search != null && !search.isBlank()) {
            String lowerSearch = search.toLowerCase();
            filteredUsers = users.stream()
                    .filter(user ->
                            (user.getName() != null && user.getName().toLowerCase().contains(lowerSearch)) ||
                            (user.getEmail() != null && user.getEmail().toLowerCase().contains(lowerSearch)) ||
                                    (user.getPhoneNumber() != null && user.getPhoneNumber().toLowerCase().contains(lowerSearch))||
            (user.getCountry() != null && user.getCountry().toLowerCase().contains(lowerSearch))
                    )
                    .toList();
        } else {
            filteredUsers = users;
        }

        // 6. Merge user and lecturer info
        return filteredUsers.stream()
                .map(user -> {
                    Lecturer lecturer = filteredLecturers.stream()
                            .filter(l -> l.getUserId().toString().equals(user.getId()))
                            .findFirst()
                            .orElse(null);
                    return lecturerMapper.toLecturerUserResponse(
                            lecturer != null ? lecturerMapper.fromLecturer(lecturer) : null,
                            user
                    );
                })
                .toList();
    }
    public List<LecturerUserResponse> getListOfFilteredLecturerUserDetails(
            List<Lecturer> lecturers,
            String display,
            String search
    ) {

        // 2. Filter lecturers by display (active/inactive/all)
        List<Lecturer> filteredLecturers;
        if ("active".equalsIgnoreCase(display)) {
            filteredLecturers = lecturers.stream()
                    .filter(l -> l.getActiveStatus() == EmploymentStatus.ACTIVE)
                    .toList();
        } else if ("inactive".equalsIgnoreCase(display)) {
            filteredLecturers = lecturers.stream()
                    .filter(l -> l.getActiveStatus() == EmploymentStatus.INACTIVE)
                    .toList();
        } else {
            filteredLecturers = lecturers;
        }

        // 3. Collect userIds from filtered lecturers
        List<UUID> userIds = filteredLecturers.stream()
                .map(Lecturer::getUserId)
                .toList();

        // 4. Fetch user info for those userIds from user service
        List<UserResponse> users = userClient.getUsersByIds(userIds);

        // 5. If search is provided, filter users by search keyword (e.g., name, email, etc.)
        List<UserResponse> filteredUsers;
        if (search != null && !search.isBlank()) {
            String lowerSearch = search.toLowerCase();
            filteredUsers = users.stream()
                    .filter(user ->
                            (user.getName() != null && user.getName().toLowerCase().contains(lowerSearch)) ||
                                    (user.getEmail() != null && user.getEmail().toLowerCase().contains(lowerSearch)) ||
                                    (user.getPhoneNumber() != null && user.getPhoneNumber().toLowerCase().contains(lowerSearch))||
                                    (user.getCountry() != null && user.getCountry().toLowerCase().contains(lowerSearch))
                    )
                    .toList();
        } else {
            filteredUsers = users;
        }

        // 6. Merge user and lecturer info
        return filteredUsers.stream()
                .map(user -> {
                    Lecturer lecturer = filteredLecturers.stream()
                            .filter(l -> l.getUserId().toString().equals(user.getId()))
                            .findFirst()
                            .orElse(null);
                    return lecturerMapper.toLecturerUserResponse(
                            lecturer != null ? lecturerMapper.fromLecturer(lecturer) : null,
                            user
                    );
                })
                .toList();
    }
    @Transactional(readOnly = false)
    public LecturerResponse updateLecturer(
            String lecturerId,
            LecturerRequest lecturerRequest
    ) {
        Lecturer lecturer = lecturerRepository.findByIdAndUserIdOrderByCreatedAtDesc(UUID.fromString(lecturerId),UUID.fromString(lecturerRequest.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        ResponseEntity<UserResponse> userResponse = userClient.getUserById(lecturer.getUserId().toString());
        if (userResponse == null) {
            throw new EntityNotFoundException("User not found for the lecturer");
        }
        Lecturer updatedLecturer = lecturerRepository.save(lecturerMapper.toLecturerUpdate(lecturer,lecturerRequest));

        return lecturerMapper.fromLecturer(updatedLecturer);
    }

   /**
     * Deletes (deactivates) a lecturer by setting status to INACTIVE and end date to now.
     *
     * @param id Lecturer ID
     * @return ResponseEntity with status message
     */
   @Transactional(readOnly = false)
    public ResponseEntity<String> deleteLecturer(String id) {
        Lecturer lecturer = lecturerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        lecturer.setActiveStatus(EmploymentStatus.INACTIVE);
        lecturer.setEndDate(LocalDate.now());
        lecturerRepository.save(lecturer);

        return ResponseEntity.ok("Lecturer has been removed successfully.");
    }

            /**
     * Restores a previously deleted (inactive) lecturer by setting status to ACTIVE and clearing end date.
     *
     * @param id Lecturer ID
     * @return ResponseEntity with status message
     */
            @Transactional(readOnly = false)
    public String restoreLecturerArchivedLecturer(
        @NotBlank(message = "Lecturer ID cannot be empty") String id,
        @NotNull(message = "Extend date cannot be empty") LocalDate extendDate) {
        
            Lecturer lecturer = lecturerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));

        if (lecturer.getActiveStatus().name().equalsIgnoreCase(EmploymentStatus.ACTIVE.name())) {
            throw  new InvalidInputException( "Lecturer is active and cannot be restored.");
        }
    if(extendDate.isAfter(LocalDate.now())) {
        throw  new InvalidInputException( "The date must be after the current date.");
    }
        lecturer.setActiveStatus(EmploymentStatus.ACTIVE);
        lecturer.setEndDate(extendDate);
        lecturerRepository.save(lecturer);

        return "Lecturer has been restored successfully.";
    }

@Transactional(readOnly = false)
    public String updateLecturerStatus(@NotBlank String id, String activeStatus) {
        Lecturer lecturer = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        if (activeStatus.equalsIgnoreCase(EmploymentStatus.ACTIVE.toString())) {
            lecturer.setActiveStatus(EmploymentStatus.ACTIVE);
        }else {
            lecturer.setActiveStatus(EmploymentStatus.valueOf(activeStatus.toUpperCase()));
            lecturer.setEndDate(LocalDate.now());
        }
        Lecturer updatedLecturer=lecturerRepository.save(lecturer);
        if(updatedLecturer.getActiveStatus() != EmploymentStatus.ACTIVE) {
            return "Lecturer status is set to be "+updatedLecturer.getActiveStatus().toString().toLowerCase()+" from " +updatedLecturer.getEndDate().toString();
        }
        return "Lecturer has been updated successfully";
    }
@Transactional(readOnly = false)
    public String updateEngagementType(String id, String engagementType) {
        Lecturer lecturer = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        if(engagementType.equalsIgnoreCase(EngagementType.PERMANENT.toString())) {
            lecturer.setEngagementType(EngagementType.valueOf(engagementType.toUpperCase()));
            lecturer.setEndDate(null);
        }else {
            if(lecturer.getEndDate() == null&& lecturer.getEndDate().isAfter(LocalDate.now())) {
                throw  new InvalidInputException("Unable to update the status of the lecturer since the end date is bellow the date today");
            }
            lecturer.setEngagementType(EngagementType.valueOf(engagementType.toUpperCase()));
        }
        lecturerRepository.save(lecturer);
        return "Lecturer has been updated successfully";
    }
    /**
     * Extends the end date of a lecturer. The new date must be after today.
     *
     * @param id Lecturer ID
     * @param newEndDate New end date to set
     * @return ResponseEntity with status message
     */
    @Transactional(readOnly =false)
    public String extendContract(String id, LocalDate newEndDate) {
        Lecturer lecturer = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        if(lecturer.getEngagementType().name().equalsIgnoreCase(EngagementType.PERMANENT.toString())) {
           throw new  InvalidInputException( "Lecturer can not be extended since the lecturer is permanent.");
        }
        if(newEndDate.isAfter(LocalDate.now())) {
            throw  new InvalidInputException("The date must be after the current date.");
        }
        if(lecturer.getActiveStatus().name().equalsIgnoreCase(EmploymentStatus.ACTIVE.name())) {
            lecturer.setEndDate(newEndDate);
            return "Lecturer has been extended successfully";
        }else {
            throw new InvalidInputException("The lecturer has to be active for extension to be enabled");
        }
    }
@Transactional
//public List<LecturerUserResponse> getLecturersByIds(Set<String> ids) {
    public List<LecturerResponse> getLecturersByIds(Set<String> ids) {
        List<UUID>lecturerIds=ids.stream().toList().stream().filter(id->id!=null&&!id.isBlank()).map(
                UUID::fromString
        ).toList();

        // 1. Get all lecturers from DB (optionally filter by status)
        List<Lecturer> lecturers = lecturerRepository.findAllByIdIn(lecturerIds);

        // 3. Collect userIds from filtered lecturers
//        List<UUID> userIds = lecturers.stream()
//                .map(Lecturer::getUserId)
//                .toList();

        // 4. Fetch user info for those userIds from user service
//        List<UserResponse> users = userClient.getUsersByIds(userIds);
//
//
//        // 6. Merge user and lecturer info
////        TODO refactor duplication methods
//        return users.stream()
//                .map(user -> {
//                    Lecturer lecturer = lecturers.stream()
//                            .filter(l -> l.getUserId().toString().equals(user.getId()))
//                            .findFirst()
//                            .orElse(null);
//                    return lecturerMapper.toLecturerUserResponse(
//                            lecturer != null ? lecturerMapper.fromLecturer(lecturer) : null,
//                            user
//                    );
//                })
//                .toList();
return lecturerMapper.fromLecturerList(lecturers);
    }

    //    public List<LecturerUserResponse> getAllLecturers(String search, String display) {
    public List<LecturerResponse> getAllLecturers(String search, String display) {
        List<Lecturer>lecturers=lecturerRepository.findAll();
        return lecturers.stream().map(lecturerMapper::fromLecturer).toList();
//       return getListOfFilteredLecturerUserDetails(lecturers,display,search);
    }
@Transactional(readOnly = false)
    public String deactivateLecturer(String id) {
        Lecturer lecturer=getEntity(id).orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
//        TODO  add hard delete
        lecturer.setActiveStatus(EmploymentStatus.INACTIVE);
        lecturer.setEndDate(LocalDate.now());
        lecturerRepository.save(lecturer);
        return "Lecturer has been archived successfully";
    }
}
