package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponentMaterial;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialResponse;

import java.util.UUID;

/**
 * MapStruct mapper for converting between LecturerComponentMaterial entities and DTOs.
 * Handles mapping of LecturerComponentMaterialRequest to LecturerComponentMaterial entity
 * and LecturerComponentMaterial entity to LecturerComponentMaterialResponse.
 */
@Mapper(componentModel = "spring",uses = {CourseMaterialMapper.class,LecturerComponentMapper.class})
public interface LecturerComponentMaterialMapper {

    LecturerComponentMaterialMapper INSTANCE = Mappers.getMapper(LecturerComponentMaterialMapper.class);

    /**
     * Converts a LecturerComponentMaterialRequest to a LecturerComponentMaterial entity.
     * Note: courseMaterialId and lecturerComponentId must be resolved to entities by the service.
     *
     * @param courseMaterial The request DTO containing course material and lecturer component IDs.
     * @return LecturerComponentMaterial entity with IDs set (entities must be set separately).
     */
    @Mapping(target = "id", ignore = true)
    LecturerComponentMaterial toLecturerComponentMaterial(
                                                          CourseMaterial courseMaterial,
                                                          LecturerComponent lecturerComponent);

    /**
     * Converts a LecturerComponentMaterial entity to a LecturerComponentMaterialResponse DTO.
     *
     * @param entity The LecturerComponentMaterial entity to convert.
     * @return LecturerComponentMaterialResponse DTO with nested CourseMaterialResponse and LecturerComponentResponse.
     */
    LecturerComponentMaterialResponse fromLecturerComponentMaterial(LecturerComponentMaterial entity);
    @Mapping(target = "id",ignore = true)
    LecturerComponentMaterial toLecturerComponentMaterialUpdate(@MappingTarget LecturerComponentMaterial lecturerComponent, CourseMaterial courseMaterial);
}