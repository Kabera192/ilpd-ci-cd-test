package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ProgramTypeMapper;
import rw.ac.ilpd.academicservice.model.sql.ProgramType;
import rw.ac.ilpd.academicservice.repository.sql.ProgramTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service class that is responsible for managing ProgramType-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramTypeService
{
    private final ProgramTypeRepository programTypeRepository;
    private final ProgramTypeMapper programTypeMapper;

    /**
     * Create and persist a ProgramType entity to the database.
     */
    public ProgramTypeResponse createProgramType(ProgramTypeRequest programTypeRequest)
    {
        log.debug("Creating new program type: {}, in service layer", programTypeRequest);

        // check if the program type name already exists in not deleted programs
        if (programTypeRepository.findByNameAndDeleteStatus(programTypeRequest.getName(),
                false).isPresent())
        {
            log.warn("Program type with the same name already exists {}", programTypeRequest);
            throw new EntityAlreadyExists("Program type with the same name already exists");
        }

        ProgramType programTypeToSave = programTypeMapper.toProgramType(programTypeRequest);
        return programTypeMapper.fromProgramType(programTypeRepository.save(programTypeToSave));
    }

    /**
     * Update the entire resource of a ProgramType
     */
    public ProgramTypeResponse updateProgramType(String programTypeId,
                                                 ProgramTypeRequest programTypeRequest)
    {
        log.debug("Updating program type: {}, in service layer", programTypeId);
        log.debug("Finding Program type: {} to update", programTypeId);

        ProgramType programType = programTypeRepository.findById(UUID
                .fromString(programTypeId)).orElse(null);

        if (programType == null)
        {
            log.warn("Could not find program type: {} to update", programTypeId);
            throw new EntityNotFoundException("ProgramType: " + programTypeId + " not found");
        }

        // find an existing program with the same name as the one in update request
        ProgramType duplicateProgramType = programTypeRepository.findByNameAndDeleteStatus(
                programTypeRequest.getName(), false
        ).orElse(null);

        // this check validates that a user does not update the name of a program to match
        // the name of another existing program.
        if (duplicateProgramType != null && !programType.getId().equals(duplicateProgramType.getId()))
        {
            log.warn("Program type with the same name already exists {}. Can't update", programTypeRequest);
            throw new EntityAlreadyExists("Program type with the same name already exists");
        }

        log.debug("Updating program type to: {}", programTypeRequest);

        programType.setName(programTypeRequest.getName());
        return programTypeMapper.fromProgramType(programTypeRepository.save(programType));
    }

    /**
     * Fetch all program types in the database with pagination and sorting by any order
     * the user wants.
     */
    public PagedResponse<ProgramTypeResponse> getAllProgramTypes(int page, int size, String sortBy, String order)
    {
        log.debug("Getting all program types from service layer");
        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<ProgramType> programTypes = programTypeRepository.findByDeleteStatus(false, pageable);

        List<ProgramTypeResponse> programTypeResponses = programTypes.getContent().stream()
                .map(programTypeMapper::fromProgramType).toList();

        return new PagedResponse<>(
                programTypeResponses,
                programTypes.getNumber(),
                programTypes.getSize(),
                programTypes.getTotalElements(),
                programTypes.getTotalPages(),
                programTypes.isLast()
        );
    }

    /**
     * Fetch a program type by the ID
     * */
    public ProgramTypeResponse getProgramTypeById(String programTypeId)
    {
        log.debug("Finding program type by id {}", programTypeId);
        ProgramType programType = programTypeRepository.findById(UUID.fromString(programTypeId)).orElse(null);

        if (programType == null)
        {
            log.warn("Could not find program type: {}", programTypeId);
            throw new EntityNotFoundException("Program type: " + programTypeId + " not found");
        }

        log.debug("Successfully found program type: {}", programTypeId);
        return programTypeMapper.fromProgramType(programType);
    }

    /**
     * Delete a program type in the database with a soft delete
     * */
    public boolean deleteProgramType(String programTypeId)
    {
        log.warn("Permanently deleting program type: {}", programTypeId);
        ProgramType programType = programTypeRepository.findById(UUID
                .fromString(programTypeId)).orElse(null);

        if (programType == null)
        {
            log.warn("Could not find program type to delete: {}", programTypeId);
            throw new EntityNotFoundException("Program type: " + programTypeId + " not found");
        }

        programTypeRepository.delete(programType);
        log.info("Deleted program type successfully: {}", programTypeId);
        return true;
    }

    /**
     * Update the deleteStatus of a program type in order to activate or deactivate it
     */
    public boolean updateDeleteStatus(String programTypeId, boolean deleteStatus)
    {
        log.debug("Permanently updating program type: {}", programTypeId);
        ProgramType programType = programTypeRepository.findById(UUID
                .fromString(programTypeId)).orElse(null);

        if (programType == null)
        {
            log.warn("Could not find program type to updateDelete: {}", programTypeId);
            throw new EntityNotFoundException("Program type: " + programTypeId + " not found");
        }

        programType.setDeleteStatus(deleteStatus);
        programTypeRepository.save(programType);
        log.info("Updated program type delete status successfully: {}", programTypeId);
        return true;
    }

    public long createListOfInitProgramTypes(List<ProgramTypeRequest> programTypeRequests) {
        List<ProgramType> unsavedProgramTypes=programTypeRequests.stream()
                .filter(pt->!programTypeRepository.existsByNameContainingIgnoreCase(pt.getName()))
                        .map(programTypeMapper::toProgramType).toList();
        if(!unsavedProgramTypes.isEmpty())
        {
          List<ProgramType>prs=  programTypeRepository.saveAll(unsavedProgramTypes);
            return prs.size();
        }else{
            return 0;
        }
    }
}
