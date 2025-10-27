package rw.ac.ilpd.academicservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.UserPriv;
import rw.ac.ilpd.mis.shared.dto.auth.response.UserProfileResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "AUTH-SERVICE")
public interface UserClient {
    @GetMapping(UserPriv.GET_USER_BY_ID_PATH)
    ResponseEntity<UserResponse> getUserById(@PathVariable String id);
    @GetMapping( path = "/api/v1/auth/users/username/{username}")
    Object getByUsername(@PathVariable String username);
    @GetMapping(UserPriv.GET_USER_BY_EMAIL_PATH)
    UserResponse getUserByEmail(@RequestParam String email);
    @PostMapping("/list")
    List<UserResponse> getUserListByUserIds(@RequestBody Set<String> id);
    @GetMapping()
    List<UserResponse> searchUsers(String search);
    @PostMapping("")
    List<UserResponse> getUsersByIds(List<UUID> userIds);
}
