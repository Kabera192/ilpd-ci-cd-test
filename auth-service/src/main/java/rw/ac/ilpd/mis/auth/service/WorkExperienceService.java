package rw.ac.ilpd.mis.auth.service;

import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceRequest;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceResponse;
public interface WorkExperienceService {

    WorkExperienceResponse create(WorkExperienceRequest request);

    WorkExperienceResponse update(String id, WorkExperienceRequest request);

    void deleteById(String id);

    PagedResponse<WorkExperienceResponse> getPrincipleUserWorkExperience(Pageable page,String search);

}
