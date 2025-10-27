package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.mapper.StudentGradeCurriculumModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.model.sql.StudentGradeCurriculumModule;
import rw.ac.ilpd.academicservice.repository.sql.StudentGradeCurriculumModuleRepository;
import rw.ac.ilpd.academicservice.repository.sql.StudentRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing StudentGradeCurriculumModule CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class StudentGradeCurriculumModuleService {

    private final StudentGradeCurriculumModuleRepository sgcModuleRepository;
    private final StudentRepository studentRepository;
    private final CurriculumModuleService cModuleService;
    private final IntakeService intakeService;
    private final StudentGradeCurriculumModuleMapper sgcModuleMapper;

    /**
     * Creates a new StudentGradeCurriculumModule.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseEntity<StudentGradeCurriculumModuleResponse> createStudentGradeCurriculumModule(
            StudentGradeCurriculumModuleRequest request
    ) {
        Student student=getStudentById(request.getStudentId());
//      find curriculum module
        CurriculumModule cModule=getActiveCurriculumModule(request.getCurriculumModuleId());
//        find intake
        Intake intake=getIntakeById(request.getIntakeId());
//Map the result to StudentGradeCurriculumModule
        StudentGradeCurriculumModule sgcModule = sgcModuleMapper.toStudentGradeCurriculumModule(student,cModule,intake,request);

        StudentGradeCurriculumModule saved = sgcModuleRepository.save(sgcModule);
        return ResponseEntity.ok(sgcModuleMapper.fromStudentGradeCurriculumModule(saved));
    }

    /**
     * Retrieves a StudentGradeCurriculumModule by ID.
     */
    public ResponseEntity<StudentGradeCurriculumModuleResponse> getStudentGradeCurriculumModule(String id) {
        StudentGradeCurriculumModule module = sgcModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("StudentGradeCurriculumModule not found"));
        return ResponseEntity.ok(sgcModuleMapper.fromStudentGradeCurriculumModule(module));
    }

    /**
     * Retrieves all StudentGradeCurriculumModules, with optional search on student name or curriculum module name.
     *
     * @param search Optional search keyword (applied to student name or module name)
     * @return ResponseEntity with list of StudentGradeCurriculumModuleResponse
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<StudentGradeCurriculumModuleResponse>> getAllStudentGradeCurriculumModules(String search) {
        List<StudentGradeCurriculumModule> modules;

        if (search == null || search.isBlank()) {
            modules = sgcModuleRepository.findAll();
        } else {
            modules = sgcModuleRepository.findByCurriculumModuleModuleNameContainingIgnoreCase(
                search
            );
        }

        List<StudentGradeCurriculumModuleResponse> response = modules.stream()
                .map(sgcModuleMapper::fromStudentGradeCurriculumModule)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a StudentGradeCurriculumModule by ID.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseEntity<StudentGradeCurriculumModuleResponse> updateStudentGradeCurriculumModule(
            String id,
            StudentGradeCurriculumModuleRequest request
    ) {
        StudentGradeCurriculumModule module = sgcModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("StudentGradeCurriculumModule not found"));
        Student student=getStudentById(request.getStudentId());
//      find curriculum module
        CurriculumModule cModule=getActiveCurriculumModule(request.getCurriculumModuleId());
//        find intake
        Intake intake=getIntakeById(request.getIntakeId());
//      Map the result to StudentGradeCurriculumModule
        StudentGradeCurriculumModule updated = sgcModuleMapper
                .toStudentGradeCurriculumModuleUpdate(module,student,cModule,intake, request);

        StudentGradeCurriculumModule saved = sgcModuleRepository.save(updated);
        return ResponseEntity.ok(sgcModuleMapper.fromStudentGradeCurriculumModule(saved));
    }

    /**
     * Deletes a StudentGradeCurriculumModule by ID.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseEntity<String> deleteStudentGradeCurriculumModule(String id) {
        StudentGradeCurriculumModule module = sgcModuleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Student Grade Curriculum Module not found"));
        sgcModuleRepository.delete(module);
        return ResponseEntity.ok("Student GradeCurriculum Module deleted successfully.");
    }

    /**
     * Retrieves paged StudentGradeCurriculumModules with optional search, sorting, and order by.
     *
     * @param page Page number
     * @param size Page size
     * @param sort Sort field
     * @param orderBy Sort order ("asc" or "desc")
     * @param search Optional search keyword (applied to student name or module name)
     * @return ResponseEntity with paged StudentGradeCurriculumModuleResponse
     */
    @Transactional(readOnly = true)
    public ResponseEntity<PagedResponse<StudentGradeCurriculumModuleResponse>> getPagedStudentGradeCurriculumModules(
            int page,
            int size,
            String sort,
            String orderBy,
            String search
    ) {
        Sort.Direction direction = orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<StudentGradeCurriculumModule> modulePage;

        if (search == null || search.isBlank()) {
            modulePage = sgcModuleRepository.findAll(pageable);
        } else {
            // You need to implement this method in your repository
            modulePage = sgcModuleRepository.findByCurriculumModuleModuleNameContainingIgnoreCase(search, pageable);
        }

        List<StudentGradeCurriculumModuleResponse> content = modulePage.getContent().stream()
                .map(sgcModuleMapper::fromStudentGradeCurriculumModule)
                .collect(Collectors.toList());

        PagedResponse<StudentGradeCurriculumModuleResponse> response = new PagedResponse<>(
                content,
                modulePage.getNumber(),
                modulePage.getSize(),
                modulePage.getTotalElements(),
                modulePage.getTotalPages(),
                modulePage.isLast()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a list of StudentGradeCurriculumModule entries in the database.
     *
     * @param requests List of StudentGradeCurriculumModuleRequest DTOs
     * @return ResponseEntity with list of created StudentGradeCurriculumModuleResponse
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResponseEntity<List<StudentGradeCurriculumModuleResponse>> createStudentGradeCurriculumModuleList(
            @Valid List<StudentGradeCurriculumModuleRequest> requests
    ) {
        List<StudentGradeCurriculumModuleResponse> responses = requests.stream().map(request -> {
            Student student = studentRepository.findById(UUID.fromString(request.getStudentId()))
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));

            CurriculumModule cModule = cModuleService.findByIdAndIsDeleted(UUID.fromString(request.getCurriculumModuleId()), false)
                    .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));

            Intake intake = intakeService.getEntity(UUID.fromString(request.getCurriculumModuleId()))
                    .orElseThrow(() -> new EntityNotFoundException("Intake not found"));

            StudentGradeCurriculumModule sgcModule = sgcModuleMapper.toStudentGradeCurriculumModule(student,cModule,intake,request);
            sgcModule.setStudent(student);
            sgcModule.setCurriculumModule(cModule);
            sgcModule.setIntake(intake);

            StudentGradeCurriculumModule saved = sgcModuleRepository.save(sgcModule);
            return sgcModuleMapper.fromStudentGradeCurriculumModule(saved);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
//    Helper
@Transactional(readOnly = true)
private CurriculumModule getActiveCurriculumModule(String id) {
    return cModuleService.findByIdAndIsDeleted(UUID.fromString(id),false).
            orElseThrow(()->new EntityNotFoundException("A specified curriculum not found"));
}
    @Transactional(readOnly = true)
    private Intake getIntakeById(String intakeId) {
        return intakeService.getEntity(UUID.fromString(intakeId)).
                orElseThrow(()->new EntityNotFoundException("A specified Intake not found"));

    }

    @Transactional(readOnly = true)
    private Student getStudentById(String id) {
        return studentRepository.findById(UUID.fromString(id)).
                orElseThrow(()->new EntityNotFoundException("A specified student not found"));
    }
}
