package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupResponse;

/**
 * This class handles logic to map an AssessmentGroupRequest to an
 * AssessmentGroup entity and map the AssessmentGroup entity from the DB
 * to an AssessmentGroupResponse object.
 */
@Component
@Slf4j
public class AssessmentGroupMapper
{
    /**
     * Converts an AssessmentGroupRequest obj to an AssessmentGroup entity.
     *
     * Parameter:
     *      AssessmentGroupRequest -> Object to be converted into a AssessmentGroup entity.
     *
     * Returns:
     *      AssessmentGroup entity object or null in case of errors in the conversion
     *      process.
     * */
    public AssessmentGroup toAssessmentGroup(
            AssessmentGroupRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null AssessmentGroupRequest");
            return null;
        }

        log.debug("Mapping AssessmentGroupRequest obj: {} to AssessmentGroup"
                , request);

        return AssessmentGroup.builder()
                .name(request.getName())
                .build();
    }

    /**
     * Converts an AssessmentGroup entity to an AssessmentGroupResponse object
     *
     * Parameter:
     *      AssessmentGroup -> Object of the AssessmentGroup entity to be converted into a
     *      AssessmentGroupResponse DTO
     *
     * Returns:
     *      AssessmentGroupResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public AssessmentGroupResponse fromAssessmentGroup(
            AssessmentGroup assessmentGroup)
    {
        if (assessmentGroup == null)
        {
            log.warn("Attempted to map null AssessmentGroup object");
            return null;
        }

        log.debug("Mapping AssessmentGroup: {} to AssessmentGroupResponse object"
                , assessmentGroup);

        return AssessmentGroupResponse.builder()
                .id(assessmentGroup.getId().toString())
                .name(assessmentGroup.getName())
                .build();
    }
}
