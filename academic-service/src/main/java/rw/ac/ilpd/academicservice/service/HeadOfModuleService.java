package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.HeadOfModuleMapper;
import rw.ac.ilpd.academicservice.mapper.ModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.HeadOfModule;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.academicservice.repository.sql.HeadOfModuleRepository;
import rw.ac.ilpd.mis.shared.util.errors.InvalidInputException;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerUserResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserModuleDetailResponse;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;
import rw.ac.ilpd.sharedlibrary.enums.EngagementType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing HeadOfModule CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class HeadOfModuleService {
    private final HeadOfModuleRepository headOfModuleRepository;
    private final HeadOfModuleMapper headOfModuleMapper;
    private final ModuleMapper moduleMapper;
    private final LecturerService lecturerService;
    private final ModuleService moduleService;
    /**
     * Creates a new HeadOfModule.
     */
    @Transactional(readOnly = false)
    public ResponseEntity<HeadOfModuleResponse> createHeadOfModule(HeadOfModuleRequest request) {
        Lecturer lecturer=lecturerService.getEntity(request.getLecturerId()).orElseThrow(()->new EntityNotFoundException("Lecturer not found"));
        Module module=moduleService
                .findByIdAndDeleteStatus(request.getModuleId(),false)
                .orElseThrow(()->new EntityNotFoundException("A specified module not found"));

        if(lecturer.getActiveStatus().equals(EmploymentStatus.INACTIVE)){
            throw new InvalidInputException("Need active lecturer for a lecturer to be a head of module");
        }else if(!lecturer.getEngagementType().equals(EngagementType.PERMANENT)){
            throw new InvalidInputException(lecturer.getEngagementType().toString().toLowerCase()+" lecturer can not be a head of module");
        }
//        verify if lecturer already exists on a specified module
        boolean existLecturer=headOfModuleRepository.existsByLecturerIdAndModuleIdAndToIsNull(UUID.fromString(request.getLecturerId()),UUID.fromString(request.getModuleId()));
       if(existLecturer){
           throw new EntityAlreadyExists("Lecturer already exists as a head of module on this specified module");
       };
//       verify if module already has a head of module lecturer
        boolean existsByModuleIdAndToIsNull=headOfModuleRepository.existsByModuleIdAndToIsNull(UUID.fromString(request.getLecturerId()));
       if(existsByModuleIdAndToIsNull){
            throw new EntityAlreadyExists("Module Already have heading lecturer");
        };
        HeadOfModule headOfModule = headOfModuleMapper.toHeadOfModule(request);
        headOfModule.setLecturer(lecturer);
        headOfModule.setModule(module);
        HeadOfModule savedModule = headOfModuleRepository.save(headOfModule);
        return new ResponseEntity<>(headOfModuleMapper.fromHeadOfModule(savedModule), HttpStatus.OK);
    }

    /**
     * Retrieves a HeadOfModule by ID.
     */
    @Transactional(readOnly = true)
    public ResponseEntity<HeadOfModuleResponse> getHeadOfModule(String id) {
        HeadOfModule head = headOfModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("HeadOfModule not found"));
        return ResponseEntity.ok(headOfModuleMapper.fromHeadOfModule(head));
    }

    /**
     * Retrieves all HeadOfModules with optional search and display filters.
     * Display can be "ACTIVE", "ALL", or "ARCHIVE".
     */
//    public ResponseEntity<List<UserModuleDetailResponse>> getAllHeadOfModules(String search, String display) {
    @Transactional(readOnly = true)
    public List<HeadOfModuleResponse> getAllHeadOfModules(String search, String display) {
        List<HeadOfModule> hod = new ArrayList<>();
        if (display.equalsIgnoreCase("active")) {
            if(search.isBlank()) {
                hod = headOfModuleRepository.findByToIsNullOrToAfter(LocalDateTime.now());
            }else {
                hod= headOfModuleRepository.findByToIsNullOrToAfter(LocalDateTime.now());
            }
//        } else  (display.equalsIgnoreCase("inactive")) {
        }else{
            hod = headOfModuleRepository.findByToIsNotNull();
        }
//        else {
//            hod = headOfModuleRepository.findAll();
//        }
//        List<LecturerUserResponse> lecturerResponses = lecturerService.getListOfFilteredLecturerUserDetails(
//                hod.stream().map(HeadOfModule::getLecturer).toList(), display, search);
//
//        List<UserModuleDetailResponse> userModuleDetailResponses = new ArrayList<>();
//        for (int i = 0; i < hod.size(); i++) {
//            HeadOfModule headOfModule = hod.get(i);
//            LecturerUserResponse lecturerUserResponse = lecturerResponses.size() > i ? lecturerResponses.get(i) : null;
//            UserModuleDetailResponse userModuleDetailResponse = new UserModuleDetailResponse();
//            userModuleDetailResponse.setUser(lecturerUserResponse.getUser());
//            userModuleDetailResponse.setModule(moduleMapper.fromModule(headOfModule.getModule()));
//            userModuleDetailResponses.add(userModuleDetailResponse);
//        }

//        return ResponseEntity.ok(userModuleDetailResponses);
        return hod.stream().map(headOfModuleMapper::fromHeadOfModule).toList();
    }

    /**
     * Retrieves paginated HeadOfModules with optional search and sorting.
     */
    @Transactional(readOnly = true)
    public PagedResponse<HeadOfModuleResponse> getPagedHeadOfModules(
            int page,
            int size,
            String sort,
            String orderBy,
            String search
    ) {
        Sort.Direction direction = orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<HeadOfModule> headOfModulePage;
//        if (search == null || search.isBlank()) {
            headOfModulePage = headOfModuleRepository.findAllByToIsNullOrToAfter(LocalDateTime.now(),pageable);
//        } else {
//            headPage = headOfModuleRepository.findByNameContainingIgnoreCase(search, pageable);
//        }

       return getPagedHeadOfModule(headOfModulePage);
    }

    /**
     * Updates a HeadOfModule by ID.
     */
    @Transactional(readOnly = false)
    public ResponseEntity<HeadOfModuleResponse> updateHeadOfModule(String id, HeadOfModuleRequest request) {
        HeadOfModule head = headOfModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("HeadOfModule not found"));
        HeadOfModule updated = headOfModuleMapper.toHeadOfModuleUpdate(head, request);
        HeadOfModule saved = headOfModuleRepository.save(updated);
        return ResponseEntity.ok(headOfModuleMapper.fromHeadOfModule(saved));
    }

    /**
     * Deletes a HeadOfModule by ID.
     */
    @Transactional(readOnly = false)
    public ResponseEntity<String> deleteHeadOfModule(String id) {
        HeadOfModule head = headOfModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Specified Head Of Module not found"));
//        TODO hard delete [the implementation is soft delete the rest is having a discussion on hard delete
//        headOfModuleRepository.delete(head);
        head.setTo(LocalDateTime.now());
        headOfModuleRepository.save(head);
        return ResponseEntity.ok("Head of module deleted successfully.");
    }

    @Transactional(readOnly = false)
    public HeadOfModuleResponse updateHeadOfModuleEndingDate(String id, LocalDateTime to) {
        if(to.isBefore(LocalDateTime.now())) {
            throw new InvalidInputException("Ending Date cannot be before now.");
        }
        HeadOfModule head = headOfModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("HeadOfModule not found"));
        head.setTo(to);
        HeadOfModule headOfModule = headOfModuleRepository.save(head);
        return headOfModuleMapper.fromHeadOfModule(headOfModule);
    }

//TODO add scheduler to remind registrar to make a follow-up and make updates accordingly
    public List<HeadOfModule> getAllHeadOfModulesHavingDeadlineBetween(LocalDateTime from, LocalDateTime to) {
        return headOfModuleRepository.findByToBetweenAndToNotNull(from,to);
    }

    public PagedResponse<HeadOfModuleResponse> getPagedHeadOfModulesHistory(Pageable pageable) {
        Page<HeadOfModule> headOfModulePage;
//        if (search == null || search.isBlank()) {
        headOfModulePage = headOfModuleRepository.findAll(pageable);
//        } else {
//            headPage = headOfModuleRepository.findByNameContainingIgnoreCase(search, pageable);
//        }
//        extract head Of Module Page
        return getPagedHeadOfModule(headOfModulePage);
    }
    private PagedResponse<HeadOfModuleResponse>  getPagedHeadOfModule(Page<HeadOfModule> headPage) {
        List<HeadOfModuleResponse> content = headPage.getContent().stream()
                .map(headOfModuleMapper::fromHeadOfModule)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                headPage.getNumber(),
                headPage.getSize(),
                headPage.getTotalElements(),
                headPage.getTotalPages(),
                headPage.isLast()
        );
    }
}
