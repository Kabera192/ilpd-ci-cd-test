package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.ProgramType;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramRequest;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;

/**
 * This class handles logic to map a ProgramRequest to a Program entity
 * and map the Program entity from the DB to a ProgramResponse object.
 * */
@Component
@Slf4j
public class ProgramMapper
{
    /**
     * Converts a ProgramRequest to a Program entity.
     * <p>
     * Parameter:
     * ProgramRequest -> Object to be converted into a Program entity.
     * <p>
     * Returns:
     * Program entity object or null in case of errors in the conversion
     * process.
     */
    public Program toProgram(ProgramRequest programRequest, ProgramType programType)
    {
        if (programRequest == null)
        {
            log.warn("Attempted to map null ProgramRequest");
            return null;
        }

        log.debug("Mapping ProgramRequest: {} to Program", programRequest);

        return Program.builder()
                .code(programRequest.getCode())
                .name(programRequest.getName())
                .description(programRequest.getDescription())
                .acronym(programRequest.getAcronym())
                .programType(programType)
                .build();
    }

    /**
     * Converts a ProgramRequest to a Program entity.
     *
     * Parameter:
     *      Program -> Object of the program entity to be converted into a
     *      response DTO
     *
     * Returns:
     *      ProgramResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public ProgramResponse fromProgram(Program program)
    {
        if (program == null)
        {
            log.warn("Attempted to map null Program object");
            return null;
        }

        log.debug("Mapping Program: {} to ProgramResponse object", program);

        return ProgramResponse.builder()
                .id(program.getId().toString())
                .code(program.getCode())
                .name(program.getName())
                .description(program.getDescription())
                .acronym(program.getAcronym())
                .programTypeId(program.getProgramType().getId().toString())
                .isDeleted(program.getIsDeleted())
                .updatedAt(program.getUpdatedAt())
                .createdAt(program.getCreatedAt())
                .build();
    }
}