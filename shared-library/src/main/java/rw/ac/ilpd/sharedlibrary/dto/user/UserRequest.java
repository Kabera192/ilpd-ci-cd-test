package rw.ac.ilpd.sharedlibrary.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.util.Date;

public class UserRequest {
    @NotBlank(message = "Name is required")
    @RestrictedString
    private String name;
    @RestrictedString
    private String title; //Sir ,Mrs ..
    @RestrictedString
    private String profilePicture;//reference from document
    @RestrictedString
    private String gender;
    private Date birthDate;
    @RestrictedString
    private String country; //reference to location id
    @RestrictedString
    private String maritalStatus;
    @RestrictedString
    private String address;
    @RestrictedString
    private String nationalIdentity;
    @RestrictedString
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    @RestrictedString
    private String email;
    @NotBlank(message = "Password is required")
    @RestrictedString
    private String password;
    @RestrictedString
    private String confirmPassword;
}
