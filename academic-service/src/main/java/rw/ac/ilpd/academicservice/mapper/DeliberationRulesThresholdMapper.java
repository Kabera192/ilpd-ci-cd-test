package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRulesThreshold;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulesthreshold.DeliberationRulesThresholdRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulesthreshold.DeliberationRulesThresholdResponse;

import java.time.LocalDateTime;
/**
 * This class handles logic to map an DeliberationRulesThresholdRequest to an
 * DeliberationRulesThreshold entity and map the DeliberationRulesThreshold entity from the DB
 * to an DeliberationRulesThresholdResponse object.
 */
@Component
@Slf4j
public class DeliberationRulesThresholdMapper
{
    /**
     * Converts an DeliberationRulesThresholdRequest obj to an DeliberationRulesThreshold entity.
     *
     * Parameter:
     *      DeliberationRulesThresholdRequest -> Object to be converted into a
     *      DeliberationRulesThreshold entity.
     *
     * Returns:
     *      DeliberationRulesThreshold entity object or null in case of errors in the conversion
     *      process.
     */
    public DeliberationRulesThreshold toDeliberationRulesThreshold(
            DeliberationRulesThresholdRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null DeliberationRulesThresholdRequest");
            return null;
        }

        log.debug("Mapping DeliberationRulesThresholdRequest obj: {} to DeliberationRulesThreshold"
                , request);

        return DeliberationRulesThreshold.builder()
                .key(request.getKey())
                .value(request.getValue())
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Converts an DeliberationRulesThreshold entity to an DeliberationRulesThresholdResponse object
     *
     * Parameter:
     *      DeliberationRulesThreshold -> Object of the DeliberationRulesThreshold
     *      entity to be converted into a DeliberationRulesThresholdResponse DTO
     *
     * Returns:
     *      DeliberationRulesThresholdResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public DeliberationRulesThresholdResponse fromDeliberationRulesThreshold(
            DeliberationRulesThreshold deliberationRulesThreshold)
    {
        if (deliberationRulesThreshold == null)
        {
            log.warn("Attempted to map null DeliberationRulesThreshold object");
            return null;
        }

        log.debug("Mapping DeliberationRulesThreshold: {} to DeliberationRulesThresholdResponse object"
                , deliberationRulesThreshold);

        return DeliberationRulesThresholdResponse.builder()
                .id(deliberationRulesThreshold.getId())
                .key(deliberationRulesThreshold.getKey())
                .value(deliberationRulesThreshold.getValue())
                .createdAt(deliberationRulesThreshold.getCreatedAt().toString())
                .build();
    }
}
