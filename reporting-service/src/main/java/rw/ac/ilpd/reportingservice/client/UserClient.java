package rw.ac.ilpd.reportingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.UserPriv;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

@FeignClient(name = "AUTH-SERVICE",path = MisConfig.AUTH_PATH + UserPriv.GET_USERS_PATH)
public interface UserClient {
    @GetMapping(path = UserPriv.GET_USER_BY_ID_PATH)
    ResponseEntity<UserResponse> getUser(@PathVariable String userId);
}
