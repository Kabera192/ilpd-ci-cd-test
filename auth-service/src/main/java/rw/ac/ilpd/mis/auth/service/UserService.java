package rw.ac.ilpd.mis.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.shared.dto.auth.response.UserProfileResponse;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.dto.user.User;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */
public interface UserService {

    User findByEmail(String email);

    User registerUser(User user);
    User updateUser(String userId, User user);

    User assignRoles(List<Role> roles, User user);
    User revokeRoles(List<Role> roles, User user);

    User assignPrivileges(List<Privilege> privileges, User user);
    User revokePrivileges(List<Privilege> privileges, User user);

    Page<User> getAllUsers(Pageable pageable);

    List<Role> getUserRoles(String userId);

    List<Privilege> getUserPrivileges(String userId);

    User getUserById(String id);

    User findByUsername(String username);

    void updateAccountLockStatus(String userId, boolean lock);

    void updateAccountExpiryStatus(String userId, boolean expire);

    void updateAccountEnabledStatus(String userId, boolean enabled);

    void updateCredentialsExpiryStatus(String userId, boolean expire);

    void updatePassword(String userId, String password);

    GoogleAuthenticatorKey generate2FASecret(String userId);

    boolean validate2FACode(String userId, int code, String loginCode);

    String getGoogleQrCodeUrl(GoogleAuthenticatorKey secret, String email);

    void enable2FA(String userId, String signUpMethod);

    void disable2FA(String userId);

    boolean verifyPassword(String username, String rawPassword);

    UserProfileResponse convertToUserProfile(User user);

    Page<User> getUnitUsers(String unitId, Pageable pageable);
    Page<User> getRoleUsers(String roleId, Pageable pageable);
    UserEntity getUserEntityById(UUID userId);
}
