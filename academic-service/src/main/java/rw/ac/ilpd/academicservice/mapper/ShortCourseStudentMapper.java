package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseStudent;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentResponse;
import rw.ac.ilpd.sharedlibrary.enums.ShortCouseStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ShortCourseStudentMapper {

    public ShortCourseStudent toShortCourseStudent(ShortCourseStudentRequest request, Intake intake) {
        return ShortCourseStudent.builder()
                .intake(intake)
                .userId(UUID.fromString(request.getUserId()))
                .status(ShortCouseStatus.valueOf(request.getStatus()))
                .build();
    }

    public ShortCourseStudent toShortCourseStudent(ShortCourseStudentRequest request, Intake intake, ShortCourseStudent existing) {
        return ShortCourseStudent.builder()
                .id(existing.getId())
                .createdAt(existing.getCreatedAt())
                .intake(intake)
                .userId(UUID.fromString(request.getUserId()))
                .status(ShortCouseStatus.valueOf(request.getStatus()))
                .build();
    }

    public ShortCourseStudentResponse fromShortCourseStudent(ShortCourseStudent scs) {
        return ShortCourseStudentResponse.builder()
                .id(scs.getId().toString())
                .intakeId(scs.getIntake().getId().toString())
                .userId(scs.getUserId().toString())
                .status(scs.getStatus().name())
                .createdAt(scs.getCreatedAt().toString())
                .build();
    }
}
