package rw.ac.ilpd.mis.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.auth.service.ExternalUserService;
import rw.ac.ilpd.mis.shared.api.AcademicExternalUserApi;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserRequest;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserResponse;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/external-users")
public class ExternalUserController implements AcademicExternalUserApi {
    private final ExternalUserService externalUserService;
    @PostMapping(value = "/appl-external-user",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<AcademicExternalUserResponse> createAcademicExternalUser(@ModelAttribute AcademicExternalUserRequest request) {
        return externalUserService.createAcademicExternalUser(request);
    }
    @PutMapping(value = "/appl-external-user/{id}",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<AcademicExternalUserResponse> updateAcademicExternalUser(@PathVariable String id,
                                                                                   @ModelAttribute AcademicExternalUserRequest request) {
        return externalUserService.updateAcademicExternalUser(id, request);
    }
    @DeleteMapping("/appl-external-user/{id}")
    @Override
    public ResponseEntity<AcademicExternalUserResponse> deleteAcademicExternalUser(@PathVariable  String id) {
        return externalUserService.deleteAcademicExternalUser(id);
    }
    @DeleteMapping("/appl-external-user/{id}/principle-user")
    @Override
    public ResponseEntity<Void> deletePrincipalAcademicExternalUserById(@PathVariable String id) {
        return externalUserService.deletePrincipalAcademicExternalUserById(id);
    }
    @GetMapping("/appl-external-user/{id}")
    @Override
    public ResponseEntity<AcademicExternalUserResponse> findById(@PathVariable String id) {
        return externalUserService.findById(id);
    }
    @GetMapping("/appl-external-user/paged")
    @Override
    public ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPagedAcademicExternalUserResponse( @RequestParam(defaultValue = "0") int page,
                                                                                                             @RequestParam(defaultValue = "10") int size,
                                                                                                             @RequestParam(defaultValue = "name") String sortBy,
                                                                                                             @RequestParam(defaultValue = "asc") String sortDirection,
                                                                                                             @RequestParam(defaultValue = "",required = false) String search) {
        return externalUserService.getPagedAcademicExternalUserResponse(page,size,sortBy,sortDirection, search);
    }

    @GetMapping("/appl-external-user/principle-user/{type}")
    @Override
    public ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPrincipalAcademicExternalUserUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")  int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
            ,@RequestParam(defaultValue = "") String search,@PathVariable String type) {
        return externalUserService.getPrincipalAcademicExternalUserUsers(page,size,sortBy,sortDirection, search,type);
    }
    @GetMapping("/appl-external-user/{id}/principle-user")
    @Override
    public ResponseEntity<AcademicExternalUserResponse> getPrincipalAcademicExternalUserById(@PathVariable String id) {
        return externalUserService.getPrincipalAcademicExternalUserById(id);
    }

}
