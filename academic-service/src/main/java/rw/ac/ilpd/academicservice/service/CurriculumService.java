package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.CurriculumMapper;
import rw.ac.ilpd.academicservice.mapper.ModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.repository.sql.CurriculumModuleRepository;
import rw.ac.ilpd.academicservice.repository.sql.CurriculumRepository;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumResponse;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.enums.CurriculumStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final ProgramService programService;
    private final CurriculumMapper curriculumMapper;
    private final CurriculumModuleRepository curriculumModuleRepository;
    private final ModuleMapper moduleMapper;

    public List<CurriculumModule> curriculumModules(UUID curriculumId){
        // check if curriculum exists
        Curriculum curriculum = getEntity(curriculumId)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum does not exist"));

        return curriculumModuleRepository.findAllByCurriculum_Id(curriculumId);
    }

//    public List<ModuleResponse> getCurriculumModules(UUID curriculumId){
//        return curriculumModules(curriculumId)
//                .stream()
//                .map(moduleMapper::fromModule)
//                .toList();
//    }

    public PagedResponse<CurriculumResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Curriculum> curriculumPage = curriculumRepository.findAll(pageable);
        return new PagedResponse<>(
                curriculumPage.getContent().stream().filter(x -> x.getStatus() == CurriculumStatus.ACTIVE).map(x -> curriculumMapper.fromCurriculum(x,
                        curriculumModules(x.getId()))).toList(),
                curriculumPage.getNumber(),
                curriculumPage.getSize(),
                curriculumPage.getTotalElements(),
                curriculumPage.getTotalPages(),
                curriculumPage.isLast()
        );
    }

    public PagedResponse<CurriculumResponse> getAllAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Curriculum> curriculumPage = curriculumRepository.findAll(pageable);
        return new PagedResponse<>(
                curriculumPage.getContent().stream().map(x -> curriculumMapper.fromCurriculum(x,
                        curriculumModules(x.getId()))).toList(),
                curriculumPage.getNumber(),
                curriculumPage.getSize(),
                curriculumPage.getTotalElements(),
                curriculumPage.getTotalPages(),
                curriculumPage.isLast()
        );
    }

    public CurriculumResponse get(UUID id) {
        return curriculumMapper.fromCurriculum(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found")), curriculumModules(id));
    }

    public CurriculumResponse create(CurriculumRequest request) {
        Program program = programService.getEntity(UUID.fromString(request.getProgramId()))
                .orElseThrow(() -> new EntityNotFoundException("Program not found"));

        // Check if curriculum with same name already exists for this program
        if (curriculumRepository.existsByNameAndProgramId(request.getName(), program.getId())) {
            throw new EntityAlreadyExists("Curriculum with this name already exists for the program");
        }

        // TODO check if user id exists and permissions, and set the createdBy

        Curriculum curriculum = curriculumMapper.toCurriculum(request, program);
        Curriculum saved = curriculumRepository.save(curriculum);
        return curriculumMapper.fromCurriculum(saved, curriculumModules(saved.getId()));
    }

    public CurriculumResponse edit(UUID id, CurriculumRequest request) {
        Curriculum existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));
        Program program = programService.getEntity(UUID.fromString(request.getProgramId()))
                .orElseThrow(() -> new EntityNotFoundException("Program not found"));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());

        existing.setProgram(program);

        Curriculum saved = curriculumRepository.save(existing);

        return curriculumMapper.fromCurriculum(saved, curriculumModules(saved.getId()));
    }

    public CurriculumResponse set_inactive(UUID id) {
        Curriculum existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));

        existing.setStatus(CurriculumStatus.INACTIVE);
        existing.setEndedAt(LocalDateTime.now());

        Curriculum saved = curriculumRepository.save(existing);

        return curriculumMapper.fromCurriculum(saved, curriculumModules(saved.getId()));
    }

    public CurriculumResponse patch(UUID id, Map<String, Object> updates) {
        Curriculum existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                existing.setName(name);
            }
        }

        // Patch description
        if (updates.containsKey("description")) {
            Object value = updates.get("description");
            if (value instanceof String description && !description.isBlank()) {
                existing.setDescription(description);
            }
        }

        // Patch programId
        if (updates.containsKey("programId")) {
            Object value = updates.get("programId");
            if (value instanceof String programId) {
                Program program = programService.getEntity(UUID.fromString(programId))
                        .orElseThrow(() -> new EntityNotFoundException("Program not found"));
                existing.setProgram(program);
            }
        }

        // createdAt should NOT be patched (auto-generated)
        Curriculum saved = curriculumRepository.save(existing);

        return curriculumMapper.fromCurriculum(saved, curriculumModules(saved.getId()));
    }

    public Boolean delete(UUID id) {
        curriculumRepository.delete(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found")));
        return true;
    }

    public Optional<Curriculum> getEntity(UUID id) {
        return curriculumRepository.findById(id);
    }
}