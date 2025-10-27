package rw.ac.ilpd.mis.shared.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;
import rw.ac.ilpd.mis.shared.dto.user.ExternalUserRequest;
import rw.ac.ilpd.mis.shared.dto.user.ExternalUserResponse;


public interface ExternalUserApi {
    ResponseEntity<ExternalUserResponse> createExternalUser(@RequestBody ExternalUserRequest request);

    ResponseEntity<ExternalUserResponse> updateExternalUser(@PathVariable String id, @RequestBody ExternalUserRequest request);

    ResponseEntity<String> deleteExternalUser(@PathVariable String id);

    ResponseEntity<PagedResponse<ExternalUserResponse>> getAllExternalUsers(int page, int size, String sort);

    ResponseEntity<PagedResponse<ExternalUserResponse>> getUsersExternalUsers(int page, int size, String sort);

    ResponseEntity<ExternalUserResponse> getExternalUserById(@PathVariable String id);
}

