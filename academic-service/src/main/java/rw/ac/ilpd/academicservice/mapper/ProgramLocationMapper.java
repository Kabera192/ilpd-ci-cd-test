package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.ProgramLocation;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationResponse;

/**
 * This class handles logic to map an ProgramLocationRequest to an
 * ProgramLocation entity and map the ProgramLocation entity from the DB
 * to an ProgramLocationResponse object.
 */
@Component
@Slf4j
public class ProgramLocationMapper
{
    /**
     * Converts an ProgramLocationRequest obj to an ProgramLocation entity.
     *
     * @param request -> Object to be converted into a ProgramLocation entity.
     *
     * @return
     *      ProgramLocation entity object or null in case of errors in the conversion
     *      process.
     */
    public ProgramLocation toProgramLocation(ProgramLocationRequest request, Program program)
    {
        if (request == null)
        {
            log.warn("Attempted to map null ProgramLocationRequest");
            return null;
        }

        log.debug("Mapping ProgramLocationRequest obj: {} to ProgramLocation"
                , request);

        return ProgramLocation.builder()
                .program(program)
                .locationId(request.getLocationId())
                .build();
    }

    /**
     * Converts an ProgramLocation entity to an ProgramLocationResponse object
     *
     * @param
     *      programLocation -> Object of the ProgramLocation entity to be converted into a
     *      ProgramLocationResponse DTO
     *
     * @return
     *      ProgramLocationResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public ProgramLocationResponse fromProgramLocation(
            ProgramLocation programLocation)
    {
        if (programLocation == null)
        {
            log.warn("Attempted to map null ProgramLocation object");
            return null;
        }

        log.debug("Mapping ProgramLocation: {} to ProgramLocationResponse object"
                , programLocation);

        return ProgramLocationResponse.builder()
                .id(programLocation.getId().toString())
                .programId(programLocation.getId().toString())
                .locationId(programLocation.getLocationId())
                .createdAt(programLocation.getCreatedAt().toString())
                .build();
    }
}
