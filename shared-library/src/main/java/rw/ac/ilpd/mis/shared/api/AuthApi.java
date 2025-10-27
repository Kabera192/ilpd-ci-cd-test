package rw.ac.ilpd.mis.shared.api;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rw.ac.ilpd.mis.shared.dto.auth.request.*;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 29/07/2025
 */
public interface AuthApi {
    ResponseEntity<?> login(@RequestBody AuthenticationRequest loginRequest);
    ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest);
    ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest);
    ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest);
    ResponseEntity<?> verify2FALogin(TotpRequest totpRequest);
    ResponseEntity<?> createPublicUser(@RequestBody CreatePublicUserRequest createPublicUserRequest);

    ResponseEntity<?> updatePublicUser(@RequestBody CreatePublicUserRequest createPublicUserRequest);
}
