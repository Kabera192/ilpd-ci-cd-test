/*
 * File: TemplateMapper.java
 *
 * Description: Mapper interface for converting between Template entity and DTOs.
 *              Uses MapStruct for automated mapping between Template, TemplateRequest, and TemplateResponse.
 *              Includes mapping for embedded DocumentMIS object.
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import rw.ac.ilpd.notificationservice.model.nosql.document.Template;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateMapper
{

    @Mapping(target = "id", ignore = true)
    Template toTemplate(TemplateRequest request);

    TemplateResponse fromTemplate(Template entity);
}