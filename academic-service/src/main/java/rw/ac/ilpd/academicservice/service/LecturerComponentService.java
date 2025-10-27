package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.LecturerComponentMapper;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.Component;
import rw.ac.ilpd.academicservice.repository.sql.LecturerComponentRepository;
import rw.ac.ilpd.academicservice.repository.sql.LecturerRepository;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LecturerComponentService {
    private final LecturerComponentRepository lecturerComponentRepository;
    private final LecturerService lecturerService;
    private final ComponentService componentService;
    private final LecturerComponentMapper lecturerComponentMapper;
    private final LecturerRepository lecturerRepository;

    public PagedResponse<LecturerComponentResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<LecturerComponent> lecturerComponentPage = lecturerComponentRepository.findAll(pageable);
        return new PagedResponse<>(
                lecturerComponentPage.getContent().stream()
                        .map(lecturerComponentMapper::fromLecturerComponent).toList(),
                lecturerComponentPage.getNumber(),
                lecturerComponentPage.getSize(),
                lecturerComponentPage.getTotalElements(),
                lecturerComponentPage.getTotalPages(),
                lecturerComponentPage.isLast()
        );
    }

    public LecturerComponentResponse get(UUID id) {
        return lecturerComponentMapper.fromLecturerComponent(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Lecturer-Component association not found")));
    }

    public LecturerComponentResponse create(LecturerComponentRequest request) {
        Lecturer lecturer = lecturerService.getEntity(request.getLecturerId())
                .orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        Component component = componentService.getEntity(UUID.fromString(request.getComponentId()))
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        // Check if this association already exists
        if (lecturerComponentRepository.existsByLecturerAndComponent(lecturer, component)) {
            throw new EntityAlreadyExists("Lecturer-Component association already exists");
        }

        // Set inactive the one that used to teach this component
        List<LecturerComponent> lecturerComponents = lecturerComponentRepository.findAllByLecturer_IdAndLecturer_ActiveStatus(lecturer.getId(), EmploymentStatus.ACTIVE);

        if(lecturerComponents.size() > 0){ // one active prof
            LecturerComponent lecturerComponent = lecturerComponents.get(0);
            Lecturer lecturer1 = lecturerComponent.getLecturer();
            lecturer1.setActiveStatus(EmploymentStatus.INACTIVE);
            lecturerRepository.save(lecturer1);

            // delete this link
            lecturerComponentRepository.deleteById(lecturerComponent.getId());
        }

        // set the current lecturer status to ACTIVE
        lecturer.setActiveStatus(EmploymentStatus.ACTIVE);
        lecturerRepository.save(lecturer);

        // save this record now
        LecturerComponent lecturerComponent = lecturerComponentMapper.toLecturerComponent(
                request, lecturer, component);
        return lecturerComponentMapper.fromLecturerComponent(
                lecturerComponentRepository.save(lecturerComponent));
    }

    public Boolean delete(UUID id) {
        LecturerComponent lecturerComponent = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Lecturer-Component association not found"));

        // Set the lecturer status to inactive
        Lecturer lecturer = lecturerComponent.getLecturer();
        lecturer.setActiveStatus(EmploymentStatus.INACTIVE);
        lecturerRepository.save(lecturer);

        lecturerComponentRepository.delete(lecturerComponent);
        return true;
    }

    public Optional<LecturerComponent> getEntity(UUID id) {
        return lecturerComponentRepository.findById(id);
    }

    public PagedResponse<LecturerComponentResponse> getAllLecturerComponentByComponentId(Pageable pageable, @ValidUuid(message = "Wrong specified component format") String componentId) {
        Page<LecturerComponent> lecturerComponentPage = lecturerComponentRepository.findByComponentId(UUID.fromString(componentId),pageable);
        return new PagedResponse<>(
                lecturerComponentPage.getContent().stream()
                        .map(lecturerComponentMapper::fromLecturerComponent).toList(),
                lecturerComponentPage.getNumber(),
                lecturerComponentPage.getSize(),
                lecturerComponentPage.getTotalElements(),
                lecturerComponentPage.getTotalPages(),
                lecturerComponentPage.isLast()
        );
    }
}