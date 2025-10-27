package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.Component;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LecturerComponentMapper {

    // Keep your existing manual method as a default method if needed
    default LecturerComponent toLecturerComponent(LecturerComponentRequest request, Lecturer lecturer, Component component) {
        return LecturerComponent.builder()
                .lecturer(lecturer)
                .component(component)
                .activeStatus(request.getActiveStatus())
                .build();
    }

    // This is the method your main mapper needs
    @Mapping(source = "id", target = "id")
    @Mapping(source = "lecturer.id", target = "lecturerId")
    @Mapping(source = "component.id", target = "componentId")
    @Mapping(source = "activeStatus", target = "activeStatus")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "formatDateTime")
    LecturerComponentResponse fromLecturerComponent(LecturerComponent lecturerComponent);

    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }
}