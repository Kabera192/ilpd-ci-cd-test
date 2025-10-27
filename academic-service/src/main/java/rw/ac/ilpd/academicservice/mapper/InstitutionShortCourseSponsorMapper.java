package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.InstitutionShortCourseSponsor;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorResponse;
import rw.ac.ilpd.sharedlibrary.enums.SponsorType;

@Component
public class InstitutionShortCourseSponsorMapper {

    public InstitutionShortCourseSponsor toInstitutionShortCourseSponsor(InstitutionShortCourseSponsorRequest request) {
        return InstitutionShortCourseSponsor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .type(SponsorType.valueOf(request.getType().toUpperCase()))
                .build();
    }

    public InstitutionShortCourseSponsorResponse fromInstitutionShortCourseSponsor(InstitutionShortCourseSponsor sponsor) {
        if(sponsor == null)
            return null;
        return InstitutionShortCourseSponsorResponse.builder()
                .id(sponsor.getId() != null ? sponsor.getId().toString() : null)
                .name(sponsor.getName())
                .email(sponsor.getEmail())
                .phone(sponsor.getPhone())
                .address(sponsor.getAddress())
                .type(sponsor.getType() != null ? sponsor.getType().name() : null)
                .build();
    }
}