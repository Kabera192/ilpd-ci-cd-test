package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface CourseMaterialMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    @Mapping(target = "isDeleted", constant = "false")
    CourseMaterial toCourseMaterial(CourseMaterialRequest courseMaterialRequest);
    @Mapping(source = "courseMaterial.createdAt", target = "createdAt", qualifiedByName = "formatDateTime")
    CourseMaterialResponse toCourseMaterialResponse(CourseMaterial courseMaterial);
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CourseMaterial toCourseMaterialUpdate(@MappingTarget CourseMaterial courseMaterial, CourseMaterialRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "courseMaterial.id",target = "id")


    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm a");
        return dateTime.format(formatter);
    }
}
