package rw.ac.ilpd.mis.auth.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.service.impl.UserDetailsImpl;
import rw.ac.ilpd.mis.shared.dto.auth.request.TotpRequest;
import rw.ac.ilpd.mis.shared.dto.user.User;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 13/07/2025
 */

public interface AuthenticationService {

    void resetPassword(String token, String newPassword);
    void generatePasswordResetToken(String email);
    UserDetailsImpl authenticate(String email, String password);
    String generateToken(UserDetailsImpl userDetails);
    void logout(String token);
    User validate2FACode(TotpRequest totpRequest);
    User registerPublicUser(User user);
    User updatePublicUser(String userId, User user);
    Object findByEmail(@NotBlank @Size(max = 50) @Email String email);

    Object findByUsername(@NotBlank @Size(min = 3, max = 20) String username);

    UserEntity getUserEntityById(UUID userId);
}
