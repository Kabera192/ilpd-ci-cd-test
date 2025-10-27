package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rw.ac.ilpd.academicservice.model.sql.ProgramType;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeResponse;

/**
 * This class handles logic to map a ProgramTypeRequest to a ProgramType entity
 * and map the ProgramType entity from the DB to a ProgramTypeResponse object.
 * */
@Component
@Slf4j
public class ProgramTypeMapper
{
    /**
     * Converts a ProgramTypeRequest to a ProgramType entity.
     *
     * Parameter:
     *      ProgramTypeRequest -> Object to be converted into a ProgramType entity.
     *
     * Returns:
     *      ProgramType entity object or null in case of errors in the conversion
     *      process.
     * */
    public ProgramType toProgramType(ProgramTypeRequest programTypeRequest)
    {
        if (programTypeRequest == null)
        {
            log.warn("Attempted to map null ProgramTypeRequest: {}", programTypeRequest);
            return null;
        }

        log.debug("Mapping ProgramTypeRequest: {} to ProgramType object", programTypeRequest);

        return ProgramType.builder()
                .name(programTypeRequest.getName())
                .build();
    }

    /**
     * Converts a ProgramType entity to a ProgramTypeResponse object.
     *
     * Parameter:
     *      ProgramType -> Object of the ProgramType entity to be converted into a
     *      ProgramTypeResponse DTO
     *
     * Returns:
     *      ProgramTypeResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public ProgramTypeResponse fromProgramType(ProgramType programType)
    {
        if (programType == null)
        {
            log.warn("Attempted to map null ProgramType object");
            return null;
        }

        log.debug("Mapping ProgramType: {} to ProgramTypeResponse object", programType);

        return ProgramTypeResponse.builder()
                .id(programType.getId().toString())
                .name(programType.getName())
                .deleteStatus(programType.isDeleteStatus())
                .createdAt(programType.getCreatedAt().toString())
                .build();
    }
}
