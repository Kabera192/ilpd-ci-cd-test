package rw.ac.ilpd.mis.shared.api;

import org.springframework.http.ResponseEntity;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceRequest;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceResponse;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;

public interface WorkExperienceApi {

    ResponseEntity<WorkExperienceResponse> create( WorkExperienceRequest request);

    ResponseEntity<WorkExperienceResponse> update(String id, WorkExperienceRequest request);
    ResponseEntity<Void> deleteById( String id);
    ResponseEntity<PagedResponse<WorkExperienceResponse>> getPrincipleUserWorkExperience(int page, int pageSize, String sortBy, String order, String search);
}