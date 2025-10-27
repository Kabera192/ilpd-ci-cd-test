package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.StudentMapper;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.repository.sql.StudentRepository;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    private final List<String> VALID_SORT_FIELDS = Arrays.asList(
            "id",
            "userId",
            "registrationNumber",
            "createdAt"
    );

    public PagedResponse<StudentResponse> getAll(int page, int size, String sortBy, String order) {

        if (!VALID_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<Student> studentPage = studentRepository.findAll(pageable);

        return new PagedResponse<>(
                studentPage.getContent().stream().map(studentMapper::fromStudent).toList(),
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast()
        );
    }

    public StudentResponse get(UUID id) {
        Student student = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return studentMapper.fromStudent(student);
    }

    public StudentResponse create(StudentRequest request) {
        UUID userId = UUID.fromString(request.getUserId());
        if (studentRepository.existsByUserId(userId)) {
            throw new EntityAlreadyExists(request.getUserId());
        }

        Student student = studentMapper.toStudent(request);
        StudentResponse response = studentMapper.fromStudent(studentRepository.save(student));

        // TODO: find the user with the userId in question

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationTypeEnum.EMAIL)
                .title("Student Created Successfully")
                .content("Warm welcome our dear new student we lovu you!")
                .destinations(Collections.singletonList(NotificationDestinationRequest.builder()
                        .userId(userId)
                        .build()))
                .build();

        String routingKey = "student.notification.create";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, notificationRequest);

        return response;
    }

    public StudentResponse edit(UUID id, StudentRequest request) {
        Student student = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        student.setRegistrationNumber(request.getRegistrationNumber());
        student.setUserId(UUID.fromString(request.getUserId()));

        return studentMapper.fromStudent(studentRepository.save(student));
    }

    public StudentResponse patch(UUID id, Map<String, Object> updates) {
        Student existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (updates.containsKey("userId")) {
            Object value = updates.get("userId");
            if (value instanceof String str) {
                try {
                    existing.setUserId(UUID.fromString(str));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for userId");
                }
            }
        }

        if (updates.containsKey("registrationNumber")) {
            Object value = updates.get("registrationNumber");
            if (value instanceof String regNum && !regNum.isBlank()) {
                existing.setRegistrationNumber(regNum);
            }
        }

        return studentMapper.fromStudent(studentRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        Student student = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        studentRepository.delete(student);
        return true;
    }

    public Optional<Student> getEntity(UUID id) {
        return studentRepository.findById(id);
    }
}
