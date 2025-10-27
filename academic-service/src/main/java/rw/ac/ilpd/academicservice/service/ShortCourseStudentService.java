package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.ShortCourseStudentMapper;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseStudent;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseStudentRepository;
import rw.ac.ilpd.academicservice.repository.sql.IntakeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentResponse;
import rw.ac.ilpd.sharedlibrary.enums.ShortCouseStatus;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortCourseStudentService {

    private final ShortCourseStudentRepository scsRepository;
    private final IntakeRepository intakeRepository;
    private final ShortCourseStudentMapper mapper;

    public PagedResponse<ShortCourseStudentResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
        Page<ShortCourseStudent> pageResult = scsRepository.findAll(pageable);

        return new PagedResponse<>(
                pageResult.getContent().stream().map(mapper::fromShortCourseStudent).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast()
        );
    }

    public ShortCourseStudentResponse get(UUID id) {
        return mapper.fromShortCourseStudent(getEntity(id));
    }

    public ShortCourseStudentResponse create(ShortCourseStudentRequest request) {
        Intake intake = intakeRepository.findById(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() -> new EntityNotFoundException("Intake not found"));

        ShortCourseStudent student = mapper.toShortCourseStudent(request, intake);
        return mapper.fromShortCourseStudent(scsRepository.save(student));
    }

    public ShortCourseStudentResponse edit(UUID id, ShortCourseStudentRequest request) {
        ShortCourseStudent existing = getEntity(id);
        Intake intake = intakeRepository.findById(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() -> new EntityNotFoundException("Intake not found"));

        ShortCourseStudent updated = mapper.toShortCourseStudent(request, intake, existing);
        return mapper.fromShortCourseStudent(scsRepository.save(updated));
    }

    public ShortCourseStudentResponse patch(UUID id, Map<String, Object> updates) {
        ShortCourseStudent existing = getEntity(id);

        if (updates.containsKey("userId")) {
            Object value = updates.get("userId");
            if (value instanceof String str && !str.isBlank()) {
                try {
                    existing.setUserId(UUID.fromString(str));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for userId");
                }
            }
        }

        if (updates.containsKey("status")) {
            Object value = updates.get("status");
            if (value instanceof String str && !str.isBlank()) {
                try {
                    existing.setStatus(ShortCouseStatus.valueOf(str));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid status value");
                }
            }
        }

        if (updates.containsKey("intakeId")) {
            Object value = updates.get("intakeId");
            if (value instanceof String str && !str.isBlank()) {
                Intake intake = intakeRepository.findById(UUID.fromString(str))
                        .orElseThrow(() -> new EntityNotFoundException("Intake not found"));
                existing.setIntake(intake);
            }
        }

        return mapper.fromShortCourseStudent(scsRepository.save(existing));
    }


    public Boolean delete(UUID id) {
        scsRepository.delete(getEntity(id));
        return true;
    }

    public ShortCourseStudent getEntity(UUID id) {
        return scsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ShortCourseStudent not found"));
    }
}
