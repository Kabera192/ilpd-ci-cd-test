package rw.ac.ilpd.mis.shared.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.enums.Gender;
import rw.ac.ilpd.mis.shared.enums.MaritalStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project misold
 * @date 06/08/2025
 */
public class CreateUserRequest {
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

    @NotBlank
    @Size(max = 50)
    @Email
    public String email;

    @NotBlank
    @Size(min = 3, max = 20)
    public String username;

    public String shortCourseStudentPosition;

    @NotBlank
    @Size(min = 6, max = 40)
    public String password;

    public boolean isActive;

    public Set<UUID> roleIds;

    public Set<UUID> privilegeIds;

    public User toUser(){
        User user = new User();
        user.setNames(this.names);
        user.setTitle(this.title);
        user.setProfilePicture(this.profilePicture);
        user.setGender(this.gender);
        user.setDateOfBirth(this.dateOfBirth);
        user.setCountry(this.country);
        user.setMaritalStatus(this.maritalStatus);
        user.setAddress(this.address);
        user.setInstitution(this.institution);
        user.setNationalIdentity(this.nationalIdentity);
        user.setPhoneNumber(this.phoneNumber);
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setShortCourseStudentPosition(this.shortCourseStudentPosition);
        user.setPassword(this.password);
        user.setActive(this.isActive);
        user.setRoleIds(this.roleIds);
        user.setPrivilegeIds(this.privilegeIds);
        return user;
    }
}
