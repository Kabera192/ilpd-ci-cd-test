package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class StudentMapper {
    public Student toStudent(StudentRequest request) {
        return Student.builder()
                .userId(UUID.fromString(request.getUserId()))
                .registrationNumber(request.getRegistrationNumber())
                .build();
    }

    public StudentResponse fromStudent(Student student) {
        return StudentResponse.builder()
                .id(student.getId() != null ? student.getId().toString() : null)
                .userId(student.getUserId() != null ? student.getUserId().toString() : null)
                .registrationNumber(student.getRegistrationNumber())
                .createdAt(student.getCreatedAt().toString())
                .build();
    }
}
