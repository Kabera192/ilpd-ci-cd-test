package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.config.RabbitMQConfig;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ModuleMapper;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.HeadOfModule;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.academicservice.repository.sql.ModuleRepository;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.event.DeletedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Creates a new module if it does not already exist.
     * Checks for duplicate code and name before saving.
     */
    @Transactional
    public ResponseEntity<ModuleResponse> createModule(ModuleRequest moduleRequest) {
        // Check if a module with the same code already exists
        findByCodeAndDeleteStatus(moduleRequest.getCode(),false).ifPresent(module -> {
            throw new EntityAlreadyExists("Module code "+module.getCode()+" is for "+module.getName()+" Module please find another module code");
        });
        // Check if a module with the same name and code already exists
        findByNameAndCodeAndDeleteStatus(moduleRequest,false)
                .stream().findFirst()
                .ifPresent(module -> {
                    throw new EntityAlreadyExists("Module "+module.getName()+" having code "+module.getCode()+" already exists");
        });
        // Save new module and return its response DTO
        Module module = moduleRepository.save(moduleMapper.toModule(moduleRequest));
        return ResponseEntity.ok().body(moduleMapper.fromModule(module));
    }

    /**
     * Finds a module by its UUID string.
     */
    public Optional<Module> findById(String id) {
        return moduleRepository.findById(UUID.fromString(id));
    }
    /**
     * Find module by Id and delete Status
     * */
    public Optional<Module> findByIdAndDeleteStatus(String id,boolean deleteStatus) {
        return moduleRepository.findByIdAndDeleteStatus(UUID.fromString(id),deleteStatus);
    }

    public Optional<Module> getEntity(UUID id) {
        return moduleRepository.findById(id);
    }


    /**
     * Finds a module by name, code, and delete status.
     */
    public Optional<Module> findByNameAndCodeAndDeleteStatus(ModuleRequest moduleRequest,boolean deleteStatus) {
        return moduleRepository.findByNameIgnoreCaseAndCodeIgnoreCaseAndDeleteStatus(
            moduleRequest.getName(), moduleRequest.getCode(), deleteStatus);
    }

    /**
     * Finds a module by code and delete status.
     */
    public Optional<Module> findByCodeAndDeleteStatus(String code,boolean deleteStatus) {
        return moduleRepository.findByCodeIgnoreCaseAndDeleteStatus(
           code,deleteStatus);
    }

    /**
     * Finds a module by ID and returns its response DTO.
     */
    public ResponseEntity<ModuleResponse> getEntity(String id) {
        return findById(id)
                .map(module -> ResponseEntity.ok(moduleMapper.fromModule(module)))
                .orElse(ResponseEntity.notFound().build());
    }
private void findAllByIdAndCodeNotContainingIgnoreCase(String id,String courseCode){
       List<Module>modules= moduleRepository.findAllByIdAndCodeNotContainingIgnoreCase(UUID.fromString(id),courseCode);
}
    /**
     * Updates an existing module.
     */
    public ResponseEntity<ModuleResponse> updateModule(String id, ModuleRequest moduleRequest)
    {
        Module module = findByIdAndDeleteStatus(id,false).stream().reduce((first,last)->last).orElseThrow(()->new EntityNotFoundException("Module  not found"));

        return ResponseEntity.ok(
            moduleMapper.fromModule(
                moduleRepository.save(moduleMapper.toModuleUpdate(module, moduleRequest))
            )
        );
    }

    /**
     * Deletes a module by its ID.
     */
    public ResponseEntity<String> deleteModule(
            @NotNull(message = "Module id is required") String id
    ) {
        Module module = moduleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Module not found"));

        List<HeadOfModule> headOfModules = module.getHeadOfModules();

        List<CurriculumModule> curriculumModules = module.getCurriculumModules();


        if(headOfModules.isEmpty() && curriculumModules.isEmpty()) {
            moduleRepository.delete(module);
        }else {
            module.setDeleteStatus(true);
            moduleRepository.save(module);
        }

        // create event deleted module
        DeletedEvent deletedEvent = new DeletedEvent("Module", id);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "deleted.Module",
                deletedEvent
        );

        return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
    }

    /**
     * Retrieves a paginated list of modules, optionally filtered by search and display status.
     */
    public PagedResponse<ModuleResponse> getAllModulePage(
            @Positive(message = "Page number must be positive") int page,
            @Positive(message = "Page size must be positive") int size,
            String sort, String search, String display
    ) {
        boolean deleteStatus = display.equals("archive");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        List<ModuleResponse> content = new ArrayList<>();
        Page<Module> modulePage;

        // Fetch modules based on display and search criteria
        if (display.equals("all")) {
            modulePage = search.isBlank()
                ? moduleRepository.findAll(pageable)
                : moduleRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(search, search, pageable);
        } else {
            modulePage = search.isBlank()
                ? moduleRepository.findByDeleteStatus(deleteStatus, pageable)
                : moduleRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseAndDeleteStatus(
                    search, search, deleteStatus, pageable);
        }
        // Map modules to response DTOs
        content = modulePage.getContent().stream()
                .map(moduleMapper::fromModule)
                .toList();

        // Return paged response
        return new PagedResponse<>(
                content,
                modulePage.getNumber(),
                modulePage.getSize(),
                modulePage.getTotalElements(),
                modulePage.getTotalPages(),
                modulePage.isLast()
        );
    }

    /**
     * Updates the delete status of a module.
     */
    public ResponseEntity<ModuleResponse> updateModuleDeleteStatus(
            @NotBlank(message = "Please the module is required") String id,
            boolean delete
    ) throws Exception {
        return moduleRepository.findById(UUID.fromString(id))
                .stream()
                .peek(module -> module.setDeleteStatus(delete)) // update the status
                .map(moduleRepository::save)                   // persist the change
                .map(moduleMapper::fromModule)           // convert to response
                .map(ResponseEntity::ok)                       // wrap in response
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: "));
    }
}
