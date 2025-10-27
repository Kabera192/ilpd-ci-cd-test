package rw.ac.ilpd.mis.shared.dto.auth.response;

import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.enums.Gender;
import rw.ac.ilpd.mis.shared.enums.MaritalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project misold
 * @date 06/08/2025
 */
public class UserProfileResponse {
    public UUID id;
    public String names;
    public String title;
    public String profilePicture;
    public Gender gender;
    public LocalDate dateOfBirth;
    public String country;
    public MaritalStatus maritalStatus;
    public String address;
    public String institution;
    public String nationalIdentity;
    public String phoneNumber;
    public String email;
    public String username;
    public String shortCourseStudentPosition;
    public boolean isActive;
    public boolean accountNonLocked ;
    public boolean accountNonExpired ;
    public boolean credentialsNonExpired ;
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private boolean isTwoFactorEnabled ;
    private String signUpMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Set<Role> roles;
    public Set<Privilege> privileges;

    public UserProfileResponse(UUID id,
                               String names,
                               String title,
                               String profilePicture,
                               Gender gender,
                               LocalDate dateOfBirth,
                               String country,
                               MaritalStatus maritalStatus,
                               String address,
                               String institution,
                               String nationalIdentity,
                               String phoneNumber,
                               String email,
                               String username,
                               String shortCourseStudentPosition,
                               boolean isActive,
                               boolean accountNonLocked,
                               boolean accountNonExpired,
                               boolean credentialsNonExpired,
                               LocalDate credentialsExpiryDate,
                               LocalDate accountExpiryDate,
                               boolean isTwoFactorEnabled,
                               String signUpMethod,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt,
                               Set<Role> roles,
                               Set<Privilege> privileges) {
        this.id = id;
        this.names = names;
        this.title = title;
        this.profilePicture = profilePicture;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.maritalStatus = maritalStatus;
        this.address = address;
        this.institution = institution;
        this.nationalIdentity = nationalIdentity;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.shortCourseStudentPosition = shortCourseStudentPosition;
        this.isActive = isActive;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.credentialsExpiryDate = credentialsExpiryDate;
        this.accountExpiryDate = accountExpiryDate;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.signUpMethod = signUpMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.privileges = privileges;
    }

}
