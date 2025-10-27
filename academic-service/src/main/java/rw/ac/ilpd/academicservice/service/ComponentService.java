package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ComponentMapper;
import rw.ac.ilpd.academicservice.model.sql.Component;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.repository.sql.ComponentRepository;
import rw.ac.ilpd.academicservice.repository.sql.CurriculumModuleRepository;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that is responsible for managing Component-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentService
{
    private final ComponentRepository componentRepository;
    private final CurriculumModuleRepository curriculumModuleRepository;
    private final ComponentMapper componentMapper;

    /**
     * Create and persist a Component entity to the database.
     */
    @Transactional
    public ComponentResponse createComponent(ComponentRequest componentRequest)
    {
        log.debug("Creating new component: {}, in service layer", componentRequest);

        log.debug("Finding Curriculum module: {} for the new Component", componentRequest.getCurriculumModuleId());
        // first find the CurriculumModule entity that is referenced by the Component to be saved
        CurriculumModule curriculumModule = curriculumModuleRepository.findById(
                UUID.fromString(componentRequest.getCurriculumModuleId()))
                .orElse(null);

        if (curriculumModule == null)
        {
            log.warn("Could not find Curriculum module: {}", componentRequest.getCurriculumModuleId());
            throw new EntityNotFoundException("Curriculum module: "
                    + componentRequest.getCurriculumModuleId() + " not found");
        }
        log.debug("Curriculum module found: {}", curriculumModule);

        // check if the component name, and acronym already exist
        if (componentRepository.findByNameOrAcronym(componentRequest.getName(),
                componentRequest.getAcronym()).isPresent())
        {
            log.warn("Component with the same name/acronym already exists {}", componentRequest);
            throw new EntityAlreadyExists("Component with the same name/acronym already exists");
        }

        Component componentToSave = componentMapper.toComponent(componentRequest, curriculumModule);
        componentToSave.setCreatedAt(LocalDateTime.now());
        Component savedComponent = componentRepository.save(componentToSave);
        if (savedComponent.getCreatedAt() == null) {
            log.warn("*************** CreatedAt is still null immediately after save");
        }else{
            log.warn("**************** CreatedAt is NOT still null immediately after save");
        }
        return componentMapper.fromComponent(savedComponent);
    }

    /**
     * Update the entire resource of a Component
     */
    @Transactional
    public ComponentResponse updateComponent(String componentId, ComponentRequest componentRequest)
    {
        log.debug("Updating component: {}, in service layer", componentId);
        log.debug("Finding component: {} to update", componentId);

        Component component = componentRepository.findById(UUID
                .fromString(componentId)).orElse(null);

        if (component == null)
        {
            log.warn("Could not find component: {} to update", componentId);
            throw new EntityNotFoundException("Component: " + componentId + " not found");
        }

        log.debug("Mapping sent curriculum module: {}", componentRequest.getCurriculumModuleId());
        CurriculumModule curriculumModule = curriculumModuleRepository.findById(
                UUID.fromString(componentRequest.getCurriculumModuleId())).orElse(null);

        if (curriculumModule == null)
        {
            log.warn("""
                            Could not find curriculum module: {} to update component: {}"""
                    , componentRequest.getCurriculumModuleId(), componentId);
            throw new EntityNotFoundException("Curriculum Module: "
                    + componentRequest.getCurriculumModuleId() + " not found");
        }

        // find an existing component with the same name/acronym as the one in update request
        Component duplicateComponent = componentRepository.findByNameOrAcronym(
                componentRequest.getName(), componentRequest.getAcronym()).orElse(null);

        // this check validates that a user does not update the name/acronym of a program to match
        // the name/acronym of another existing program.
        if (duplicateComponent != null && !component.getId().equals(duplicateComponent.getId()))
        {
            log.warn("Component with the same name/acronym already exists {}. Can't update", componentRequest);
            throw new EntityAlreadyExists("Component with the same name/acronym already exists");
        }

        log.debug("Updating component to: {}", componentRequest);

        component.setName(componentRequest.getName());
        component.setAcronym(componentRequest.getAcronym());
        component.setCredits(componentRequest.getCredits());
        component.setCurriculumModule(curriculumModule);
        return componentMapper.fromComponent(componentRepository.save(component));
    }

    /**
     * Fetch all components in the database with pagination and sorting by any order
     * the user wants.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ComponentResponse> getAllComponents(int page, int size, String sortBy, String order)
    {
        log.debug("Getting all components from service layer");

        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<Component> components = componentRepository.findAll(pageable);

        List<ComponentResponse> componentResponses = components.getContent().stream()
                .map(componentMapper::fromComponent).toList();

        return new PagedResponse<>(
                componentResponses,
                components.getNumber(),
                components.getSize(),
                components.getTotalElements(),
                components.getTotalPages(),
                components.isLast()
        );
    }

    /**
     * Fetch a component by the ID
     */
    @Transactional(readOnly = true)
    public ComponentResponse getComponentById(String componentId)
    {
        log.debug("Finding component by id {}", componentId);
        Component component = componentRepository.findById(
                UUID.fromString(componentId)).orElse(null);

        if (component == null)
        {
            log.warn("Could not find component: {}", componentId);
            throw new EntityNotFoundException("Component: " + componentId + " not found");
        }

        log.debug("Successfully found component: {}", component);
        return componentMapper.fromComponent(component);
    }

    /**
     * Delete a component in the database permanently
     */
    @Transactional
    public boolean deleteComponent(String componentId)
    {
        log.warn("Permanently deleting component: {}", componentId);
        Component component = componentRepository.findById(UUID.fromString(componentId)).orElse(null);

        if (component == null)
        {
            log.warn("Could not find component to delete: {}", componentId);
            throw new EntityNotFoundException("Component: " + componentId + " not found");
        }

        componentRepository.delete(component);
        log.info("Deleted component successfully: {}", component);
        return true;
    }

    public Optional<Component> getEntity(UUID id){
        return componentRepository.findById(id);
    }
}
