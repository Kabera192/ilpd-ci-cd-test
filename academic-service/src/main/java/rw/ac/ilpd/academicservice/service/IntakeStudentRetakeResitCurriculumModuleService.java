package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.IntakeStudentRetakeResitCurriculumModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeStudentRetakeResitCurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.repository.sql.IntakeStudentRetakeResitCurriculumModuleRepository;
import rw.ac.ilpd.academicservice.service.IntakeService;
import rw.ac.ilpd.academicservice.service.StudentService;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.enums.ModuleRetakeResitType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntakeStudentRetakeResitCurriculumModuleService {
    private final IntakeStudentRetakeResitCurriculumModuleRepository repository;
    private final StudentService studentService;
    private final IntakeService intakeService;
    private final CurriculumModuleService curriculumModuleService;
    private final IntakeStudentRetakeResitCurriculumModuleMapper mapper;

    public PagedResponse<IntakeStudentRetakeRestCurriculumModuleResponse> getAll(
            int page, int size, String sortBy, String order
    ) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<IntakeStudentRetakeResitCurriculumModule> pageResult = repository.findAll(pageable);
        return new PagedResponse<>(
                pageResult.getContent().stream().map(mapper::fromIntakeStudentRetakeResitCurriculumModule).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }

    public IntakeStudentRetakeRestCurriculumModuleResponse get(UUID id) {
        return mapper.fromIntakeStudentRetakeResitCurriculumModule(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("Retake/Resit record not found"))
        );
    }

    public IntakeStudentRetakeRestCurriculumModuleResponse create(
            IntakeStudentRetakeRestCurriculumModuleRequest request
    ) {
        // Fetch related entities using their services
        Student student = studentService.getEntity(UUID.fromString(request.getStudentId()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() -> new EntityNotFoundException("Intake not found"));

        CurriculumModule curriculumModule = curriculumModuleService.getEntity(UUID.fromString(request.getCurriculumModuleId()))
                .orElseThrow(() -> new EntityNotFoundException("Curriculum module not found"));


        // Check if record already exists
        if (repository.existsByStudentAndIntakeAndCurriculumModule(student, intake, curriculumModule)) {
            throw new EntityAlreadyExists("Retake/Resit record already exists for this student, intake, and curriculum module");
        }

        IntakeStudentRetakeResitCurriculumModule entity = mapper.toIntakeStudentRetakeResitCurriculumModule(
                request, student, intake, curriculumModule
        );
        return mapper.fromIntakeStudentRetakeResitCurriculumModule(repository.save(entity));
    }

    public IntakeStudentRetakeRestCurriculumModuleResponse edit(
            UUID id, IntakeStudentRetakeRestCurriculumModuleRequest request
    ) {
        IntakeStudentRetakeResitCurriculumModule existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Retake/Resit record not found"));

        // Fetch related entities using their services
        Student student = studentService.getEntity(UUID.fromString(request.getStudentId()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() -> new EntityNotFoundException("Intake not found"));
        CurriculumModule curriculumModule = curriculumModuleService.getEntity(UUID.fromString(request.getCurriculumModuleId()))
                .orElseThrow(() -> new EntityNotFoundException("Curriculum module not found"));

        // Check if another record exists with same combination (excluding current one)
        if (repository.existsByStudentAndIntakeAndCurriculumModuleAndIdNot(student, intake, curriculumModule, id)) {
            throw new EntityAlreadyExists("Another retake/resit record already exists for this student, intake, and curriculum module");
        }

        existing.setStudent(student);
        existing.setIntake(intake);
        existing.setCurriculumModule(curriculumModule);
        existing.setModuleRetakeResitType(ModuleRetakeResitType.valueOf(request.getType()));

        return mapper.fromIntakeStudentRetakeResitCurriculumModule(repository.save(existing));
    }

    public IntakeStudentRetakeRestCurriculumModuleResponse patch(
            UUID id, Map<String, Object> updates
    ) {
        IntakeStudentRetakeResitCurriculumModule existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Retake/Resit record not found"));

        // Patch student
        if (updates.containsKey("studentId")) {
            Object value = updates.get("studentId");
            if (value instanceof String studentId) {
                Student student = studentService.getEntity(UUID.fromString(studentId))
                        .orElseThrow(() -> new EntityNotFoundException("Student not found"));
                existing.setStudent(student);
            }
        }

        // Patch intake
        if (updates.containsKey("intakeId")) {
            Object value = updates.get("intakeId");
            if (value instanceof String intakeId) {
                Intake intake = intakeService.getEntity(UUID.fromString(intakeId))
                        .orElseThrow(() -> new EntityNotFoundException("Intake not found"));

                existing.setIntake(intake);
            }
        }

        // Patch curriculum module
        if (updates.containsKey("curriculumModuleId")) {
            Object value = updates.get("curriculumModuleId");
            if (value instanceof String curriculumModuleId) {
                CurriculumModule curriculumModule = curriculumModuleService.getEntity(UUID.fromString(curriculumModuleId))
                        .orElseThrow(() -> new EntityNotFoundException("Curriculum module not found"));

                existing.setCurriculumModule(curriculumModule);
            }
        }

        // Patch type
        if (updates.containsKey("type")) {
            Object value = updates.get("type");
            if (value instanceof String type) {
                try {
                    existing.setModuleRetakeResitType(ModuleRetakeResitType.valueOf(type));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid module retake/resit type: " + type);
                }
            }
        }

        return mapper.fromIntakeStudentRetakeResitCurriculumModule(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        repository.delete(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Retake/Resit record not found")));
        return true;
    }

    public Optional<IntakeStudentRetakeResitCurriculumModule> getEntity(UUID id) {
        return repository.findById(id);
    }

    // Additional business methods if needed
    public boolean existsByStudentAndIntakeAndCurriculumModule(Student student, Intake intake, CurriculumModule module) {
        return repository.existsByStudentAndIntakeAndCurriculumModule(student, intake, module);
    }
}