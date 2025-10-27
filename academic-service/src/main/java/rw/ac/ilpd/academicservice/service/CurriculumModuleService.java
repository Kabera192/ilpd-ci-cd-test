package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.ConflictException;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.CurriculumModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.academicservice.repository.sql.CurriculumModuleRepository;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CurriculumModuleService {
    private final CurriculumModuleRepository curriculumModuleRepository;
    private final CurriculumService curriculumService;
    private final ModuleService moduleService;
    private final CurriculumModuleMapper curriculumModuleMapper;

    public PagedResponse<CurriculumModuleResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<CurriculumModule> curriculumModulePage = curriculumModuleRepository.findAll(pageable);
        return new PagedResponse<>(
                curriculumModulePage.getContent().stream()
                        .map(curriculumModuleMapper::fromCurriculumModule).toList(),
                curriculumModulePage.getNumber(),
                curriculumModulePage.getSize(),
                curriculumModulePage.getTotalElements(),
                curriculumModulePage.getTotalPages(),
                curriculumModulePage.isLast()
        );
    }

    public CurriculumModuleResponse get(UUID id) {
        return curriculumModuleMapper.fromCurriculumModule(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum Module not found")));
    }

    public CurriculumModuleResponse create(CurriculumModuleRequest request) {
        Curriculum curriculum = curriculumService.getEntity(UUID.fromString(request.getCurriculumId()))
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));
        Module module = moduleService.getEntity(UUID.fromString(request.getModuleId()))
                .orElseThrow(() -> new EntityNotFoundException("Module not found"));

        // Check if this module already exists in the curriculum
        if (curriculumModuleRepository.existsByCurriculumAndModule(curriculum, module)) {
            throw new EntityAlreadyExists("Module already exists in this curriculum");
        }

        // set an order number
        int max_order = curriculumModuleRepository.findAllByCurriculum_Id(UUID.fromString(request.getCurriculumId()))
                .stream()
                .mapToInt(CurriculumModule::getModuleOrder)
                .max()
                .orElse(0);

        CurriculumModule curriculumModule = curriculumModuleMapper.toCurriculumModule(request, curriculum, module);
        curriculumModule.setModuleOrder(max_order + 1);

        return curriculumModuleMapper.fromCurriculumModule(curriculumModuleRepository.save(curriculumModule));
    }

    public CurriculumModuleResponse edit(UUID id, CurriculumModuleRequest request) {
        CurriculumModule existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum Module not found"));
        Curriculum curriculum = curriculumService.getEntity(UUID.fromString(request.getCurriculumId()))
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));
        Module module = moduleService.getEntity(UUID.fromString(request.getModuleId()))
                .orElseThrow(() -> new EntityNotFoundException("Module not found"));

        // check if the order number does not exist already
        if(!Objects.equals(existing.getModuleOrder(), request.getModuleOrder()) && curriculumModuleRepository.existsByCurriculumAndModuleOrder(curriculum, request.getModuleOrder())){
            throw new ConflictException(
                    String.format("Another module in curriculum '%s' already uses order %d",
                            curriculum.getName(), request.getModuleOrder())
            );
        }

        existing.setCurriculum(curriculum);
        existing.setModule(module);
        if(request.getModuleOrder() != null)
            existing.setModuleOrder(request.getModuleOrder());
        existing.setCredits(request.getCredits());

        return curriculumModuleMapper.fromCurriculumModule(curriculumModuleRepository.save(existing));
    }

    public CurriculumModuleResponse patch(UUID id, Map<String, Object> updates) {
        CurriculumModule existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum Module not found"));

        // Patch curriculumId
        if (updates.containsKey("curriculumId")) {
            Object value = updates.get("curriculumId");
            if (value instanceof String curriculumId) {
                Curriculum curriculum = curriculumService.getEntity(UUID.fromString(curriculumId))
                        .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));
                existing.setCurriculum(curriculum);
            }
        }

        // Patch moduleId
        if (updates.containsKey("moduleId")) {
            Object value = updates.get("moduleId");
            if (value instanceof String moduleId) {
                Module module = moduleService.getEntity(UUID.fromString(moduleId))
                        .orElseThrow(() -> new EntityNotFoundException("Module not found"));
                existing.setModule(module);
            }
        }

        // Patch moduleOrder
        if (updates.containsKey("moduleOrder")) {
            int value = (int) updates.get("moduleOrder");

            // check if the order number does not already exist
            if(!Objects.equals(existing.getModuleOrder(), value) && curriculumModuleRepository.existsByCurriculumAndModuleOrder(existing.getCurriculum(), value))
                throw new ConflictException(
                        String.format("Another module in curriculum '%s' already uses order %d",
                                existing.getCurriculum().getName(), value)
                );
            existing.setModuleOrder(value);
        }

        // Patch credits
        if (updates.containsKey("credits")) {
            Object value = updates.get("credits");
            if (value instanceof Integer credits) {
                existing.setCredits(credits);
            }
        }

        return curriculumModuleMapper.fromCurriculumModule(curriculumModuleRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        curriculumModuleRepository.delete(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Curriculum Module not found")));
        return true;
    }

    public Optional<CurriculumModule> getEntity(UUID id) {
        return curriculumModuleRepository.findById(id);
    }

    // Additional method if there was a deletedStatus column
    public Optional<CurriculumModule> findByIdAndIsDeleted(UUID id, boolean isDeleted) {
        // This would be used if the entity had a deletedStatus column
        // return curriculumModuleRepository.findByIdAndIsDeleted(id, isDeleted);
        // For now, just use the regular findById
        return curriculumModuleRepository.findById(id);
    }
}