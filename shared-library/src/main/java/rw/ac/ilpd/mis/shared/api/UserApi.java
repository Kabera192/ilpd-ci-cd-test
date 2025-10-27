package rw.ac.ilpd.mis.shared.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import rw.ac.ilpd.mis.shared.dto.auth.request.ChangePasswordRequest;
import rw.ac.ilpd.mis.shared.dto.auth.request.CreateUserRequest;
import rw.ac.ilpd.mis.shared.dto.auth.request.TotpRequest;
import rw.ac.ilpd.mis.shared.dto.auth.request.UpdateUserRequest;
import rw.ac.ilpd.mis.shared.dto.user.User;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 29/07/2025
 */
public interface UserApi {

    ResponseEntity<?> getAllUsers(int page, int size, String sort);
    ResponseEntity<?> getUnitUsers(String unitId, int page, int size, String sort);
    ResponseEntity<?> getRoleUsers(String roleId, int page, int size, String sort);
    /**
     * Sample usage: "curl $HOST:$PORT/$AUTH_PATH/users/1".
     *
     * @param userId Id of the user
     * @return the user, if found, else null
     */
    ResponseEntity<?> getUser(String userId);
    ResponseEntity<?> getByUsername( String username);
    ResponseEntity<?> getByEmail( String keyword);
    ResponseEntity<?> createUser(CreateUserRequest body);
    ResponseEntity<?> updateUser( String userId, UpdateUserRequest body);
    ResponseEntity<?> enable2FAEmail();
    ResponseEntity<?> enable2FAApp();
    ResponseEntity<?> get2FAStatus();
    ResponseEntity<?> disable2FA();
    ResponseEntity<?> updatePassword(ChangePasswordRequest changePasswordRequest);
    ResponseEntity<?> currentUsername(UserDetails userDetails);
    ResponseEntity<?> getUserDetails(UserDetails userDetails);


}
