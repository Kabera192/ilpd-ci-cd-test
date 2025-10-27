package rw.ac.ilpd.mis.shared.dto.auth.request;

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
public class UpdateUserRequest {
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
    public Set<UUID> roleIds;
    public Set<UUID> privilegeIds;

    public User toUser(){
        User user = new User();
        user.setId(this.id);
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
        user.setActive(this.isActive);
        user.setRoleIds(this.roleIds);
        user.setPrivilegeIds(this.privilegeIds);
        return user;
    }
}
