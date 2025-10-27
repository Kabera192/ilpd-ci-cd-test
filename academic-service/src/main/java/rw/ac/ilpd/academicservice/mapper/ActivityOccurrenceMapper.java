package rw.ac.ilpd.academicservice.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityOccurrence;
import rw.ac.ilpd.sharedlibrary.dto.activityoccurence.ActivityOccurenceRequest;
import rw.ac.ilpd.sharedlibrary.dto.activityoccurence.ActivityOccurenceResponse;
import rw.ac.ilpd.sharedlibrary.dto.attendancemissing.AttendanceMissingResponse;

import java.util.List;
import java.util.UUID;

/**
 * This class handles logic to map an ActivityOccurrenceRequest to an
 * ActivityOccurrence entity and map the ActivityOccurrence entity from the DB
 * to an ActivityOccurrenceResponse object.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ActivityOccurrenceMapper
{
    private final AttendanceMissingMapper attendanceMissingMapper;

    /**
     * Converts an ActivityOccurrenceRequest obj to an ActivityOccurrence entity.
     * <p>
     * Parameter:
     * ActivityOccurrenceRequest -> Object to be converted into a ActivityOccurrence entity.
     * <p>
     * Returns:
     * ActivityOccurrence entity object or null in case of errors in the conversion
     * process.
     */
    public ActivityOccurrence toActivityOccurrence(
            ActivityOccurenceRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null ActivityOccurrenceRequest");
            return null;
        }

        log.debug("Mapping ActivityOccurrenceRequest obj: {} to ActivityOccurrence"
                , request);

        return ActivityOccurrence.builder()
                .day(request.getDay())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .lecturerId(UUID.fromString(request.getLecturerId()))
                .hasDoneAttendance(request.getHasDoneAttendance())
                .build();
    }

    /**
     * Converts an ActivityOccurrence entity to an ActivityOccurrenceResponse object
     * <p>
     * Parameter:
     * ActivityOccurrence -> Object of the ActivityOccurrence entity to be converted into a
     * ActivityOccurrenceResponse DTO
     * <p>
     * Returns:
     * ActivityOccurrenceResponse object to the caller or null if an error is
     * encountered during the mapping process.
     */
    public ActivityOccurenceResponse fromActivityOccurrence(
            ActivityOccurrence activityOccurrence)
    {
        List<AttendanceMissingResponse> attendanceMissingResponseList;

        if (activityOccurrence == null)
        {
            log.warn("Attempted to map null ActivityOccurrence object");
            return null;
        }

        if (activityOccurrence.getAttendanceMissings() == null)
        {
            log.warn("No attendance missing included in this occurrence");
            attendanceMissingResponseList = null;
        }
        else
        {
            attendanceMissingResponseList = activityOccurrence
                    .getAttendanceMissings().stream()
                    .map(attendanceMissingMapper::fromAttendanceMissing).toList();
        }

        log.debug("Mapping ActivityOccurrence: {} to ActivityOccurrenceResponse object"
                , activityOccurrence);

        return ActivityOccurenceResponse.builder()
                .id(activityOccurrence.getId())
                .lecturerId(activityOccurrence.getLecturerId().toString())
                .hasDoneAttendance(activityOccurrence.getHasDoneAttendance())
                .day(activityOccurrence.getDay())
                .startTime(activityOccurrence.getStartTime())
                .endTime(activityOccurrence.getEndTime())
                .deletedStatus(activityOccurrence.getDeletedStatus())
                .attendanceMissingResponses(attendanceMissingResponseList)
                .build();
    }
}
