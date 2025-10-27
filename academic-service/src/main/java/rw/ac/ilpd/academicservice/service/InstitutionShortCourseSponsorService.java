package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.InstitutionShortCourseSponsorMapper;
import rw.ac.ilpd.academicservice.model.sql.InstitutionShortCourseSponsor;
import rw.ac.ilpd.academicservice.repository.sql.InstitutionShortCourseSponsorRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorResponse;
import rw.ac.ilpd.sharedlibrary.enums.SponsorType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstitutionShortCourseSponsorService {
    private final InstitutionShortCourseSponsorRepository sponsorRepository;
    private final InstitutionShortCourseSponsorMapper sponsorMapper;

    public PagedResponse<InstitutionShortCourseSponsorResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc"))
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        else
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<InstitutionShortCourseSponsor> sponsorPage = sponsorRepository.findAll(pageable);
        return new PagedResponse<>(
                sponsorPage.getContent().stream().map(sponsorMapper::fromInstitutionShortCourseSponsor).toList(),
                sponsorPage.getNumber(),
                sponsorPage.getSize(),
                sponsorPage.getTotalElements(),
                sponsorPage.getTotalPages(),
                sponsorPage.isLast()
        );
    }

    public InstitutionShortCourseSponsorResponse get(UUID id) {
        return sponsorMapper.fromInstitutionShortCourseSponsor(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("Sponsor not found"))
        );
    }

    public InstitutionShortCourseSponsorResponse create(InstitutionShortCourseSponsorRequest request) {
        // Check if sponsor with same name already exists
        if (sponsorRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExists("Sponsor with name '" + request.getName() + "' already exists");
        }

        InstitutionShortCourseSponsor sponsor = sponsorMapper.toInstitutionShortCourseSponsor(request);
        return sponsorMapper.fromInstitutionShortCourseSponsor(sponsorRepository.save(sponsor));
    }

    public InstitutionShortCourseSponsorResponse edit(UUID id, InstitutionShortCourseSponsorRequest request) {
        InstitutionShortCourseSponsor existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Sponsor not found"));

        // Check if another sponsor with the same name exists (excluding current one)
        if (sponsorRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new EntityAlreadyExists("Another sponsor with name '" + request.getName() + "' already exists");
        }

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setAddress(request.getAddress());
        existing.setType(SponsorType.valueOf(request.getType().toUpperCase()));

        return sponsorMapper.fromInstitutionShortCourseSponsor(sponsorRepository.save(existing));
    }

    public InstitutionShortCourseSponsorResponse patch(UUID id, Map<String, Object> updates) {
        InstitutionShortCourseSponsor existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Sponsor not found"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                // Check if another sponsor with the same name exists
                if (sponsorRepository.existsByNameAndIdNot(name, id)) {
                    throw new EntityAlreadyExists("Another sponsor with name '" + name + "' already exists");
                }
                existing.setName(name);
            }
        }

        // Patch email
        if (updates.containsKey("email")) {
            Object value = updates.get("email");
            if (value instanceof String email) {
                existing.setEmail(email);
            }
        }

        // Patch phone
        if (updates.containsKey("phone")) {
            Object value = updates.get("phone");
            if (value instanceof String phone) {
                existing.setPhone(phone);
            }
        }

        // Patch address
        if (updates.containsKey("address")) {
            Object value = updates.get("address");
            if (value instanceof String address) {
                existing.setAddress(address);
            }
        }

        // Patch type
        if (updates.containsKey("type")) {
            Object value = updates.get("type");
            if (value instanceof String type) {
                try {
                    existing.setType(SponsorType.valueOf(type));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid sponsor type: " + type);
                }
            }
        }

        return sponsorMapper.fromInstitutionShortCourseSponsor(sponsorRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        sponsorRepository.delete(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Sponsor not found")));
        return true;
    }
    @Transactional(readOnly = true)
    public Optional<InstitutionShortCourseSponsor> getEntity(UUID id) {
        return sponsorRepository.findById(id);
    }
}