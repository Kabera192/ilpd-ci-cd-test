package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturer.LecturerUserResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class handles logic to map a LecturerRequest to a
 * Lecturer entity and map the Lecturer entity from the DB
 * to a LecturerResponse object.
 * */
@Mapper(componentModel = "spring",uses = {DateMapperFormatter.class})
public interface LecturerMapper
{
    @Mapping(target = "activeStatus",constant = "ACTIVE")
        @Mapping(target = "createdAt",ignore = true)
        @Mapping(target = "updatedAt",ignore = true)
      Lecturer toLecturer(LecturerRequest lecturerRequest);

        @Named("fullLecturer")
        @Mapping(target = "endDate",qualifiedByName = "formatDate")
        @Mapping(target = "createdAt",qualifiedByName = "formatDateTime")
      LecturerResponse fromLecturer(Lecturer lecturer);

        LecturerResponse fromShortCourseTopicLecturer(Lecturer lecturer);

      @Mapping(target = "createdAt",ignore = true)
      @Mapping(target = "updatedAt",ignore = true)
      Lecturer toLecturerUpdate(@MappingTarget Lecturer lecturer, LecturerRequest lecturerRequest);

      @Mappings({
            @Mapping(target ="lecturer",source = "lecturer"),
            @Mapping(target = "user",source = "userResponse")
        })
    LecturerUserResponse toLecturerUserResponse(LecturerResponse lecturer, UserResponse userResponse);

    @IterableMapping(qualifiedByName = "fullLecturer")
    List<LecturerResponse> fromLecturerList(List<Lecturer> lecturers);
}
