package rw.ac.ilpd.mis.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.mis.auth.entity.jpa.*;
import rw.ac.ilpd.mis.auth.repository.jpa.InvalidatedTokenRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.PasswordResetTokenRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.security.jwt.JwtUtils;
import rw.ac.ilpd.mis.auth.service.*;
import rw.ac.ilpd.mis.shared.config.privilege.auth.PublicPriv;
import rw.ac.ilpd.mis.shared.dto.auth.request.TotpRequest;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.util.errors.AuthException;
import rw.ac.ilpd.mis.shared.util.errors.ErrorCode;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 30/07/2025
 */

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${frontend.url}")
    String frontendUrl;

    @Value("${password.reset.token.expiry.hours}")
    int tokenExpiryHours;


    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;


    @Override
    public void generatePasswordResetToken(String email){
        try{
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = UUID.randomUUID().toString();
            Instant expiryDate = Instant.now().plus(tokenExpiryHours, ChronoUnit.HOURS);
            PasswordResetToken resetToken = new PasswordResetToken(token, expiryDate, user);
            passwordResetTokenRepository.save(resetToken);

            String resetUrl = frontendUrl + "/reset-password?token=" + token;
            // Send email to user
            emailService.sendPasswordResetEmail(user.getEmail(), resetUrl);
            LOG.debug("User password reset token generated, user : {}", user);

        } catch (Exception e) {
            LOG.error("Generate password reset token failed due to exception: error={}", e);
        }

    }

    @Override
    public UserDetailsImpl authenticate(String username, String password) {
        Authentication authentication;
        try {
            var user = userRepository
                    .findByUsername(username)
                    .or(() -> userRepository.findByEmail(username))
                    .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_EXISTED));

            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));
            boolean authenticated = authentication.isAuthenticated();
            if (!authenticated) {
                LOG.error("User not authenticated, invalid username or password");
                throw new AuthException(ErrorCode.USERNAME_OR_PASSWORD_INVALID);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserDetailsImpl) authentication.getPrincipal();
        }catch (AuthException e) {
            LOG.error("Authentication failed due to exception: ", e);
            throw new AuthException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public String generateToken(UserDetailsImpl userDetails) {
        try{
            return jwtUtils.generateToken(userDetails);
        } catch (Exception e) {
            LOG.error("Generate token failed due to exception: ", e);
            throw new AuthException(ErrorCode.UNAUTHENTICATED);
        }

    }

    @Override
    public void logout(String token) {
        try{
            var signToken = jwtUtils.verifyToken(token, true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            UserEntity user = userRepository.findByUsername(signToken.getJWTClaimsSet().getSubject()).get();
            if(user == null) throw new AuthException(ErrorCode.UNAUTHENTICATED);

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(UUID.fromString(jit)).expiryTime(expiryTime).user(user).build();

            invalidatedTokenRepository.save(invalidatedToken);
            LOG.info("User {} has been invalidated, and logged out successfully", user);

        } catch (Exception e) {
            LOG.error("Logout failed due to exception: ", e);
            throw new AuthException(ErrorCode.LOGOUT_FAILED);
        }
    }

    @Override
    public User validate2FACode(TotpRequest totpRequest) {
        User user = userService.findByUsername(totpRequest.getUsername());
        if( userService.validate2FACode(String.valueOf(user.getId()), totpRequest.getCode(), totpRequest.getLoginCode())) {
            return user;
        }
        return null;
    }


    @Override
    public void resetPassword(String token, String newPassword) {
        try{
            PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                    .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

            if (resetToken.isUsed())
                throw new RuntimeException("Password reset token has already been used");

            if (resetToken.getExpiryDate().isBefore(Instant.now()))
                throw new RuntimeException("Password reset token has expired");

            UserEntity user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            resetToken.setUsed(true);
            passwordResetTokenRepository.save(resetToken);
            LOG.debug("User password reset success, user : {}", user);
        } catch (Exception e) {
            LOG.error("Password reset failed due to exception: error={}", e);
        }
    }

    @Override
    public User registerPublicUser(User user){
        try{
            LOG.info("Register public user : {}", user);
            Role publicRole = roleService.getRoleName("Public");
            Privilege publicPriv = privilegeService.getPrivilegeName(PublicPriv.PUBLIC);
            Set<UUID> roles = new HashSet<>();
            Set<UUID> privileges = new HashSet<>();
            if (publicRole != null)  roles.add(publicRole.getId());
            if (publicPriv != null)  privileges.add(publicPriv.getId());
            user.setRoleIds(roles);
            user.setPrivilegeIds(privileges);
            User registeredUser = userService.registerUser(user);
            LOG.debug("Registering public user success, user : {}", registeredUser);
            return registeredUser;
        }catch (Exception e) {
            LOG.error("Registering public user failed due to exception: error={}", e);
            throw e;
        }
    }

    @Override
    public User updatePublicUser(String userId, User user){
        try{
            LOG.info("Update public user : {}", user);
            Role publicRole = roleService.getRoleName("Public");
            Privilege publicPriv = privilegeService.getPrivilegeName(PublicPriv.PUBLIC);
            Set<UUID> roles = new HashSet<>();
            Set<UUID> privileges = new HashSet<>();
            if (publicRole != null)  roles.add(publicRole.getId());
            if (publicPriv != null)  privileges.add(publicPriv.getId());
            user.setRoleIds(roles);
            user.setPrivilegeIds(privileges);
            User updatedUser = userService.updateUser(user.getId().toString(), user);
            LOG.debug("Updating public user success, user : {}", updatedUser);
            return updatedUser;
        }catch (Exception e) {
            LOG.error("Updating public user failed due to exception: error={}", e);
            return null;
        }


    }

    @Override
    public Object findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public Object findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public UserEntity getUserEntityById(UUID userId){
        return userService.getUserEntityById(userId);
    }


}
