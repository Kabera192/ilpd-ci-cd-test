package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ProgramMapper;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.ProgramType;
import rw.ac.ilpd.academicservice.repository.sql.ProgramRepository;
import rw.ac.ilpd.academicservice.repository.sql.ProgramTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramRequest;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that is responsible for managing Program-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramService
{
    private final ProgramRepository programRepository;
    private final ProgramTypeRepository programTypeRepository;
    private final ProgramMapper programMapper;

    /**
     * Create and persist a Program entity to the database.
     */
    public ProgramResponse createProgram(ProgramRequest programRequest)
    {
        log.debug("Creating new program: {}, in service layer", programRequest);

        log.debug("Finding program type: {} for the new program", programRequest.getProgramTypeId());
        // first, find the ProgramType entity that references the Program to be saved
        ProgramType programType = programTypeRepository.findById(UUID.fromString(
                programRequest.getProgramTypeId())).orElse(null);

        if (programType == null)
        {
            log.warn("Could not find program type: {}", programRequest.getProgramTypeId());
            throw new EntityNotFoundException("Program type provided does not exist");
        }

        // check if the program name, code and acronym already exist in not deleted programs
        checkIfDuplicate(programRequest);

        Program programToSave = programMapper.toProgram(programRequest, programType);
        return programMapper.fromProgram(programRepository.save(programToSave));
    }

    /**
     * Utility function that checks whether a program request object is
     * a duplicate of an existing program by name or code or acronym
     *
     * @throws EntityAlreadyExists exception when the program already exists
     *                             by name or code or acronym
     */
    private void checkIfDuplicate(ProgramRequest programRequest)
    {
        // check if the program name, code and acronym already exist in not deleted programs
        Program duplicateProgram = programRepository.findByNameOrCodeOrAcronymAndIsDeleted(
                programRequest.getName(), programRequest.getCode(),
                programRequest.getAcronym(), false
        ).orElse(null);

        if (duplicateProgram != null)
        {
            if (duplicateProgram.getName().equals(programRequest.getName()))
            {
                log.warn("Program with the same name already exists {}", programRequest);
                throw new EntityAlreadyExists("Program with the same name already exists");
            }
            else if (duplicateProgram.getCode().equals(programRequest.getCode()))
            {
                log.warn("Program with the same code already exists {}", programRequest);
                throw new EntityAlreadyExists("Program with the same code already exists");
            }
            else if (duplicateProgram.getAcronym().equals(programRequest.getAcronym()))
            {
                log.warn("Program with the same acronym already exists {}", programRequest);
                throw new EntityAlreadyExists("Program with the same acronym already exists");
            }
        }
    }

    /**
     * Update the entire resource of a Program
     */
    public ProgramResponse updateProgram(String programId, ProgramRequest programRequest)
    {
        log.debug("Updating program: {}, in service layer", programId);
        log.debug("Finding Program: {} to update", programId);

        Program program = getEntity(UUID.fromString(programId)).orElse(null);

        if (program == null)
        {
            log.warn("Could not find program: {} to update", programId);
            throw new EntityNotFoundException("Program provided does not exist");
        }

        log.debug("Mapping program type request {} to program", programRequest);
        ProgramType programType = programTypeRepository.findById(UUID.fromString(
                programRequest.getProgramTypeId())).orElse(null);

        if (programType == null)
        {
            log.warn("""
                            Could not find program type: {} to update Program: {}"""
                    , programRequest.getProgramTypeId(), programId);
            throw new EntityNotFoundException("Program type provided does not exist");
        }

        // find an existing program with the same name/code/acronym as the one in update request
        boolean codeHasAlreadyTaken=programRepository.existsByIdNotAndCode(program.getId(),programRequest.getCode());
        if(codeHasAlreadyTaken){
            log.warn("Program with the same code already exists {}", programRequest);
            throw new EntityAlreadyExists("Program with the same code already exists");
        }
//        checkIfDuplicate(programRequest);

        log.debug("Updating program to: {}", programRequest);

        program.setName(programRequest.getName());
        program.setCode(programRequest.getCode());
        program.setAcronym(programRequest.getAcronym());
        program.setDescription(programRequest.getDescription());
        program.setProgramType(programType);
        return programMapper.fromProgram(programRepository.save(program));
    }

    /**
     * Fetch all programs in the database with pagination and sorting by any order
     * the user wants.
     */
    public PagedResponse<ProgramResponse> getAllPrograms(int page, int size, String sortBy, String order)
    {
        log.debug("Getting all programs from service layer");
        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<Program> programs = programRepository.findByIsDeleted(false, pageable);

        List<ProgramResponse> programResponses = programs.getContent().stream()
                .map(programMapper::fromProgram).toList();

        return new PagedResponse<>(
                programResponses,
                programs.getNumber(),
                programs.getSize(),
                programs.getTotalElements(),
                programs.getTotalPages(),
                programs.isLast()
        );
    }

    /**
     * Fetch a program by the ID
     */
    public ProgramResponse getProgramById(String programId)
    {
        log.debug("Finding program by id {}", programId);
        Program program = programRepository.findByIdAndIsDeleted(
                UUID.fromString(programId), false).orElse(null);

        if (program == null)
        {
            log.warn("Could not find program: {}", programId);
            throw new EntityNotFoundException("Program provided does not exist");
        }

        log.debug("Successfully found program: {}", program);
        return programMapper.fromProgram(program);
    }

    /**
     * Utility function to fetch a program by id used by other functions
     */
    public Optional<Program> getEntity(UUID programId)
    {
        log.debug("Finding program by id {}", programId);
        return programRepository.findByIdAndIsDeleted(programId, false);
    }

    /**
     * Delete a program in the database with a soft delete
     */
    public boolean deleteProgram(String programId)
    {
        log.warn("Permanently deleting program: {}", programId);
        Program program = programRepository.findById(UUID.fromString(programId)).orElse(null);

        if (program == null)
        {
            log.warn("Could not find program to delete: {}", programId);
            throw new EntityNotFoundException("Program provided does not exist");
        }

        programRepository.delete(program);
        log.info("Deleted program successfully: {}", programId);
        return true;
    }

    /**
     * Update the deleteStatus of a program in order to activate or deactivate it
     */
    public boolean updateDeleteStatus(String programId, boolean deleteStatus)
    {
        log.debug("Permanently updating program: {}", programId);
        Program program = programRepository.findById(UUID.fromString(programId)).orElse(null);

        if (program == null)
        {
            log.warn("Could not find program to updateDelete: {}", programId);
            throw new EntityNotFoundException("Program provided does not exist");
        }

        program.setIsDeleted(deleteStatus);
        programRepository.save(program);
        log.info("Updated program delete status successfully: {}", programId);
        return true;
    }
}
