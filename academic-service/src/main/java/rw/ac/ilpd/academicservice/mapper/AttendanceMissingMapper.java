package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AttendanceMissing;
import rw.ac.ilpd.sharedlibrary.dto.attendancemissing.AttendanceMissingRequest;
import rw.ac.ilpd.sharedlibrary.dto.attendancemissing.AttendanceMissingResponse;

import java.util.UUID;

/**
 * This class handles logic to map an AttendanceMissingRequest to an
 * AttendanceMissing entity and map the AttendanceMissing entity from the DB
 * to an AttendanceMissingResponse object.
 */
@Component
@Slf4j
public class AttendanceMissingMapper
{
    /**
     * Converts an AttendanceMissingRequest obj to an AttendanceMissing entity.
     *
     * Parameter:
     *      AttendanceMissingRequest -> Object to be converted into a AttendanceMissing entity.
     *
     * Returns:
     *      AttendanceMissing entity object or null in case of errors in the conversion
     *      process.
     */
    public AttendanceMissing toAttendanceMissing(
            AttendanceMissingRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null AttendanceMissingRequest");
            return null;
        }

        log.debug("Mapping AttendanceMissingRequest obj: {} to AttendanceMissing"
                , request);

        return AttendanceMissing.builder()
                .userId(UUID.fromString(request.getUserId()))
                .build();
    }

    /**
     * Converts an AttendanceMissing entity to an AttendanceMissingResponse object
     *
     * Parameter:
     *      AttendanceMissing -> Object of the AttendanceMissing entity to be converted into a
     *      AttendanceMissingResponse DTO
     *
     * Returns:
     *      AttendanceMissingResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public AttendanceMissingResponse fromAttendanceMissing(
            AttendanceMissing attendanceMissing)
    {
        if (attendanceMissing == null)
        {
            log.warn("Attempted to map null AttendanceMissing object");
            return null;
        }

        log.debug("Mapping AttendanceMissing: {} to AttendanceMissingResponse object"
                , attendanceMissing);

        return AttendanceMissingResponse.builder()
                .id(attendanceMissing.getId())
                .userId(attendanceMissing.getUserId().toString())
                .createdAt(attendanceMissing.getCreatedAt().toString())
                .build();
    }
}
