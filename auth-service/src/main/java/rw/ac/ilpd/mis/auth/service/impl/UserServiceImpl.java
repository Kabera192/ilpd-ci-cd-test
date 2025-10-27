package rw.ac.ilpd.mis.auth.service.impl;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.PasswordResetTokenRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.PrivilegeRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.RoleRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.service.EmailService;
import rw.ac.ilpd.mis.auth.service.TotpService;
import rw.ac.ilpd.mis.auth.service.UserService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.PrivilegeMapper;
import rw.ac.ilpd.mis.auth.service.impl.mapper.RoleMapper;
import rw.ac.ilpd.mis.auth.service.impl.mapper.UserMapper;
import rw.ac.ilpd.mis.shared.dto.auth.response.UserProfileResponse;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.util.errors.AuthException;
import rw.ac.ilpd.mis.shared.util.errors.ErrorCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 19/07/2025
 */

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TotpService totpService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PrivilegeMapper privilegeMapper;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        try{
            Page<UserEntity> users = userRepository.findAll(pageable);
            if (users.isEmpty()) {
                LOG.debug("No users found");
                return null;
            }
            LOG.debug("Fetched users");
            return userMapper.entityPageToApiPage(users);
        }catch (Exception e){
            LOG.error("Get users failed due to exception: error={}", e);
            return null;
        }
    }


    @Override
    public Page<User> getUnitUsers(String unitId, Pageable pageable) {
        try{
            List<RoleEntity> roles = roleRepository.findByUnitEntityId(UUID.fromString(unitId));
            Set<RoleEntity> roleSet = roles.stream().collect(Collectors.toSet());
            Page<UserEntity> users = userRepository.findByRolesIn(Collections.singleton(roleSet), pageable);
            if (users.isEmpty()) {
                LOG.debug("No users found");
                return null;
            }
            LOG.debug("Fetched users");
            return userMapper.entityPageToApiPage(users);
        }catch (Exception e){
            LOG.error("Get users failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public Page<User> getRoleUsers(String roleId, Pageable pageable) {
        try{
            RoleEntity role = roleRepository.findById(UUID.fromString(roleId)).get();
            Page<UserEntity> users = userRepository.findByRoles(role, pageable);
            if (users.isEmpty()) {
                LOG.debug("No users found for role {}", roleId);
                return null;
            }
            LOG.debug("Fetched users for role {}", roleId);
            return userMapper.entityPageToApiPage(users);
        }catch (Exception e){
            LOG.error("Get users failed due to exception: error={}", e);
            return null;
        }
    }



    @Override
    public List<Role> getUserRoles(String userId) {
        try{
            Optional<UserEntity> user = userRepository.findById(UUID.fromString(userId));
            if (user.isEmpty()) {
                LOG.debug("No user found");
                return null;
            }
            LOG.debug("Fetched user : {}", user.get());
            return roleMapper.entityListToApiList(user.get().getRoles().stream().toList());
        }catch (Exception e){
            LOG.error("Get user roles failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public List<Privilege> getUserPrivileges(String userId) {

        try{
            Optional<UserEntity> user = userRepository.findById(UUID.fromString(userId));
            if (user.isEmpty()) {
                LOG.debug("No user found");
                return null;
            }
            LOG.debug("Fetched user : {}", user.get());
            return privilegeMapper.entityListToApiList(user.get().getPrivileges().stream().toList());
        }catch (Exception e){
            LOG.error("Get user privileges failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public UserEntity getUserEntityById(UUID userId) {
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                LOG.debug("No user found");
                return null;
            }
            LOG.debug("Fetched user : {}", user.get());
            return user.get();
        }catch (Exception e){
            LOG.error("Get user failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public User getUserById(String userId) {
        try{
            Optional<UserEntity> user = userRepository.findById(UUID.fromString(userId));
            if (!user.isPresent()) {
                LOG.debug("No user found");
                return null;
            }
            LOG.debug("Fetched user : {}", user.get());
            return userMapper.entityToApi(user.get());
        }catch (Exception e){
            LOG.error("Get user failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public UserProfileResponse convertToUserProfile(User userDto) {
        UserEntity user = userRepository.findById(userDto.getId()).get();
        //LOG.info("USER ROLES : {}",  user.getRoles());
        //LOG.info("USER PRIVILEGES : {}",  user.getPrivileges());
        if (user != null) {
            Set<Privilege> privileges = new HashSet<>();
            Set<Role> roles = new HashSet<>();
            if (!CollectionUtils.isEmpty(user.getRoles())) {
                user.getRoles().forEach(role -> {
                    //LOG.info("Role Priv: {}", role.getPrivileges());
                    //Mapper won't do the job since it is auto-generated
                    // Fix implementatio, no role should have empty privileges
                    roles.add(roleMapper.entityToApi(role));
                    if (!CollectionUtils.isEmpty(role.getPrivileges()))
                        role.getPrivileges().forEach(priv -> privileges.add(privilegeMapper.entityToApi(priv)));
                });
            }
            if (!CollectionUtils.isEmpty(user.getPrivileges())){
                user.getPrivileges().forEach(priv -> privileges.add(privilegeMapper.entityToApi(priv)));
            }
            return new UserProfileResponse(
                    user.getId(),
                    user.getNames(),
                    user.getTitle(),
                    user.getProfilePicture(),
                    user.getGender(),
                    user.getDateOfBirth(),
                    user.getCountry(),
                    user.getMaritalStatus(),
                    user.getAddress(),
                    user.getInstitution(),
                    user.getNationalIdentity(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getShortCourseStudentPosition(),
                    user.isActive(),
                    user.isAccountNonLocked(),
                    user.isAccountNonExpired(),
                    user.isCredentialsNonExpired(),
                    user.getCredentialsExpiryDate(),
                    user.getAccountExpiryDate(),
                    user.isTwoFactorEnabled(),
                    user.getSignUpMethod(),
                    user.getCreatedAt(),
                    user.getUpdatedAt(),
                    roles,
                    privileges
            );
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        try{
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                LOG.debug("No user found");
                return null;
            }
            LOG.debug("Fetched user : {}", user.get());
            return userMapper.entityToApi(user.get());
        }catch (Exception e){
            LOG.error("Get user failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public void updateAccountLockStatus(String userId, boolean lock) {
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                    -> new RuntimeException("User not found"));
            user.setAccountNonLocked(!lock);
            userRepository.save(user);
            LOG.debug("Update user lock status, user : {}", user);
        }catch (Exception e){
            LOG.error("Update user lock status failed due to exception: error={}", e);
        }
    }

    @Override
    public void updateAccountExpiryStatus(String userId, boolean expire) {
        try{
                UserEntity user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                        -> new RuntimeException("User not found"));
                user.setAccountNonExpired(!expire);
                userRepository.save(user);
                LOG.debug("Update user expiry status, user : {}", user);
            }catch (Exception e){
                LOG.error("Update user expiry status failed due to exception: error={}", e);
        }
    }

    @Override
    public void updateAccountEnabledStatus(String userId, boolean enabled) {
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                    -> new RuntimeException("User not found"));
            user.setActive(enabled);
            userRepository.save(user);
            LOG.debug("Update user active status, user : {}", user);
        }catch (Exception e){
            LOG.error("Update user active status failed due to exception: error={}", e);
        }

    }

    @Override
    public void updateCredentialsExpiryStatus(String userId, boolean expire) {
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                    -> new RuntimeException("User not found"));
            user.setCredentialsNonExpired(!expire);
            userRepository.save(user);
            LOG.debug("Update user credentials status, user : {}", user);
        }catch (Exception e){
            LOG.error("Update user credentials status failed due to exception: error={}", e);
        }

    }

    @Override
    public void updatePassword(String userId, String password) {
        try {
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            LOG.debug("Update user password, user : {}", user);
        } catch (Exception e) {
            LOG.error("Update user password failed due to exception: error={}", e);
        }
    }


    @Override
    public User findByEmail(String email) {
        try{
            Optional<UserEntity> user = userRepository.findByEmail(email);
            LOG.debug("User found by email, user : {}", user);
            return userMapper.entityToApi(user.get());
        }catch (Exception e) {
            LOG.error("Retrieve user by email failed due to exception: error={}", e);
            return null;
        }

    }

    @Override
    public User registerUser(User user){
        try{
            LOG.info("Register user : {}", user);
            UserEntity userEntity = createUser(user);
            LOG.debug("Registering user success, user : {}", userEntity);
            return userMapper.entityToApi(userEntity);
        }catch (Exception e) {
            LOG.error("Registering user failed due to exception: error={}", e);
            throw e;
        }
    }

    @Override
    public User updateUser(String userId, User user){
        try{
            UserEntity userEntity = editUser(UUID.fromString(userId), user).get();
            LOG.debug("Updating user success, user : {}", userEntity);
            return userMapper.entityToApi(userEntity);
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("Updating user failed due to exception: error={}", e);
            throw e;
        }
    }

    @Transactional
    public UserEntity createUser(User dto) {
        UserEntity user = new UserEntity();
        populateUserFromDto(user, dto);
        LOG.debug("Create user success, user : {}", user);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<UserEntity> editUser(UUID userId, User dto) {
        return userRepository.findById(userId).map(user -> {
            populateUserFromDto(user, dto);
            return userRepository.save(user);
        });
    }

    @Override
    public User assignRoles(List<Role> roles, User user) {
        return null;
    }

    @Override
    public User revokeRoles(List<Role> roles, User user) {
        return null;
    }

    @Override
    public User assignPrivileges(List<Privilege> privileges, User user) {
        return null;
    }

    @Override
    public User revokePrivileges(List<Privilege> privileges, User user) {
        return null;
    }

    @Override
    public  String getGoogleQrCodeUrl(GoogleAuthenticatorKey secret, String email){
        return totpService.getGoogleQrCodeUrl(secret, email);
    }

    @Override
    public GoogleAuthenticatorKey generate2FASecret(String userId){
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            GoogleAuthenticatorKey key = totpService.generateSecret();
            user.setTwoFactorSecret(key.getKey());
            userRepository.save(user);
            LOG.debug("Generating 2FA key success, user : {}", user);
            return key;
        } catch (Exception e) {
            LOG.error("Generating 2FA key failed due to exception: error={}", e);
            return null;
        }

    }

    @Override
    public boolean validate2FACode(String userId, int code,  String loginCode){
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return totpService.verifyCode(user.getTwoFactorSecret(), code, userId , loginCode);
        } catch (Exception e) {
            LOG.error("2FA code verification failed due to exception: error={}", e);
            return false;
        }

    }

    @Override
    public void enable2FA(String userId, String signUpMethod){
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setTwoFactorEnabled(true);
            user.setSignUpMethod(signUpMethod.toLowerCase());
            userRepository.save(user);
            LOG.debug("2FA enabled successfully, user : {}", user);
        }catch (Exception e) {
            LOG.error("Enabling 2FA failed due to exception: error={}", e);
        }

    }

    @Override
    public void disable2FA(String userId){
        try{
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setTwoFactorEnabled(false);
            userRepository.save(user);
            LOG.debug("2FA disabled successfully, user : {}", user);
        }catch (Exception e) {
            LOG.error("Disabling 2FA failed due to exception: error={}", e);
        }
    }

    public boolean verifyPassword(String username, String rawPassword) {
        try{
            var user =
                    userRepository.findByUsername(username).orElseThrow(() -> new AuthException(ErrorCode.UNAUTHENTICATED));
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }catch (Exception e) {
            LOG.error("Verifying password failed due to exception: error={}", e);
            return false;
        }
    }

    private void populateUserFromDto(UserEntity user, User dto) {
        user.setNames(dto.getNames());
        user.setTitle(dto.getTitle());
        user.setProfilePicture(dto.getProfilePicture());
        user.setGender(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCountry(dto.getCountry());
        user.setMaritalStatus(dto.getMaritalStatus());
        user.setAddress(dto.getAddress());
        user.setInstitution(dto.getInstitution());
        user.setNationalIdentity(dto.getNationalIdentity());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        if(dto.getUsername() == null || "".equals(dto.getUsername())){
            user.setUsername(dto.getEmail());
        }
        user.setShortCourseStudentPosition(dto.getShortCourseStudentPosition());
        if (dto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(dto.isActive());

        // Set roles
        if (dto.roleIds != null) {
            Set<RoleEntity> roles = new HashSet<>();
            for (UUID roleId : dto.roleIds) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
            user.setRoles(roles);
        }

        // Set privileges
        if (dto.privilegeIds != null) {
            Set<PrivilegeEntity> privileges = new HashSet<>();
            for (UUID privilegeId : dto.privilegeIds) {
                privilegeRepository.findById(privilegeId).ifPresent(privileges::add);
            }
            user.setPrivileges(privileges);
        }
    }

}