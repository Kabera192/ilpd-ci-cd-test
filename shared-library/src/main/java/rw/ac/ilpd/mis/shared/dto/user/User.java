package rw.ac.ilpd.mis.shared.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.enums.Gender;
import rw.ac.ilpd.mis.shared.enums.MaritalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private UUID id;

    private String names;

    private String title;

    private String profilePicture;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String country;

    private MaritalStatus maritalStatus;

    private String address;

    private String institution;

    private String nationalIdentity;

    private String phoneNumber;

    private String email;

    private String username;

    private String shortCourseStudentPosition;

    private String password;

    @JsonAlias({"isActive","active"})
    private boolean isActive;

    private boolean accountNonLocked ;
    private boolean accountNonExpired ;
    private boolean credentialsNonExpired ;

    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;

    private String twoFactorSecret;
    private boolean isTwoFactorEnabled ;
    private String signUpMethod;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Role> roles;

    private Set<Privilege> privileges;

    public Set<UUID> roleIds;
    public Set<UUID> privilegeIds;

    public User(   UUID userId,
                   String userName,
                   String email,
                   String telephone,
                   String identityNumber,
                   boolean accountNonLocked,
                   boolean accountNonExpired,
                   boolean credentialsNonExpired,
                   boolean enabled,
                   LocalDate credentialsExpiryDate,
                   LocalDate accountExpiryDate,
                   String twoFactorSecret,
                   boolean isTwoFactorEnabled,
                   String signUpMethod,
                   Set<Role> roles,
                   Set<Privilege> privileges,
                   LocalDateTime createdDate,
                   LocalDateTime updatedDate) {

        this.id = userId;
        this.username = userName;
        this.email = email;
        this.phoneNumber = telephone;
        this.nationalIdentity = identityNumber;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.isActive = enabled;
        this.credentialsExpiryDate = credentialsExpiryDate;
        this.accountExpiryDate = accountExpiryDate;
        this.twoFactorSecret = twoFactorSecret;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.signUpMethod = signUpMethod;
        this.roles = roles;
        this.privileges = privileges;
        this.createdAt = createdDate;
        this.updatedAt = updatedDate;
    }

    public User(   String username,
                   String identityNumber,
                   String password,
                   String telephone,
                   Set<Role> roles,
                   Set<Privilege> privileges,
                   String email) {
        this.username=username;
        this.nationalIdentity=identityNumber;
        this.password=password;
        this.phoneNumber=telephone;
        this.roles = roles;
        this.privileges = privileges;
        this.email = email;
    }


    public User(   UUID id,
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
                   String password,
                   boolean isActive,
                   Set<UUID> roleIds,
                   Set<UUID> privilegeIds) {
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
        this.password = password;
        this.isActive = isActive;
        this.roleIds = roleIds;
        this.privilegeIds = privilegeIds;
    }

}
