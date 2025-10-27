package rw.ac.ilpd.mis.shared.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserRequest;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserResponse;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;

public interface AcademicExternalUserApi {
    ResponseEntity<AcademicExternalUserResponse> createAcademicExternalUser(@Valid  AcademicExternalUserRequest request);
    ResponseEntity<AcademicExternalUserResponse> updateAcademicExternalUser(String id,@Valid AcademicExternalUserRequest request);
    ResponseEntity<AcademicExternalUserResponse> deleteAcademicExternalUser(String id);
    ResponseEntity<AcademicExternalUserResponse> findById(String id);
    ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPagedAcademicExternalUserResponse( int page,
                                                                                                      int size,
                                                                                                       String sortBy,
                                                                                                       String sortDirection,
                                                                                                     String search);
    ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPrincipalAcademicExternalUserUsers(
             int page,
            int size,
            String sortBy,
            String sortDirection
            ,String search,@Pattern(regexp = "^(SPONSOR|NEXT_OF_KIN|sponsor|next_of_kin)$") String type);
    ResponseEntity<AcademicExternalUserResponse> getPrincipalAcademicExternalUserById(String id);
    ResponseEntity<Void> deletePrincipalAcademicExternalUserById(String search);

}
