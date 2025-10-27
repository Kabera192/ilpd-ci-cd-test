package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentResponse;

/**
 * This class handles logic to map a ComponentRequest to a
 * Component entity and map the Component entity from the DB
 * to a ComponentResponse object.
 */
@org.springframework.stereotype.Component
@Slf4j
public class ComponentMapper
{
    /**
     * Converts a ComponentRequest obj to a Component entity.
     *
     * Parameter:
     *      ComponentRequest -> Object to be converted into a Component entity.
     *
     * Returns:
     *      Component entity object or null in case of errors in the conversion
     *      process.
     * */
    public rw.ac.ilpd.academicservice.model.sql.Component toComponent(
            ComponentRequest request,
            CurriculumModule curriculumModule)
    {
        if (request == null)
        {
            log.warn("Attempted to map null ComponentRequest");
            return null;
        }

        log.debug("Mapping ComponentRequest obj: {} to Component"
                , request);

        return rw.ac.ilpd.academicservice.model.sql.Component.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .curriculumModule(curriculumModule)
                .credits(request.getCredits())
                .build();
    }

    /**
     * Converts a Component entity to a ComponentResponse object
     *
     * Parameter:
     *      Component -> Object of the Component entity to be converted into a
     *      ComponentResponse DTO
     *
     * Returns:
     *      ComponentResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public ComponentResponse fromComponent(
            rw.ac.ilpd.academicservice.model.sql.Component component)
    {
        if (component == null)
        {
            log.warn("Attempted to map null Component object");
            return null;
        }

        log.debug("Mapping Component: {} to ComponentResponse object"
                , component);

        return ComponentResponse.builder()
                .id(component.getId().toString())
                .name(component.getName())
                .acronym(component.getAcronym())
                .curriculumModuleId(component.getCurriculumModule().getId().toString())
                .credits(component.getCredits())
                .createdAt(component.getCreatedAt().toString())
                .build();
    }
}
