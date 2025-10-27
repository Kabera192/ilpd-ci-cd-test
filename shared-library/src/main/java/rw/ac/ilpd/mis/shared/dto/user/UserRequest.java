package rw.ac.ilpd.mis.shared.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class UserRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String title; //Sir ,Mrs ..
    private String profilePicture;//reference from document
    private String gender;
    private Date birthDate;
    private String country; //reference to location id
    private String maritalStatus;
    private String address;
    private String nationalIdentity;
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String confirmPassword;
}
