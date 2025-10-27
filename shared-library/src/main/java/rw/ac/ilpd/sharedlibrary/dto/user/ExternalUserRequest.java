/**
 * ExternalUser.java
 *  
 * This DTO class is used to represent an external user of the MIS (a user without an account).
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified 2025-07-03
 */
package rw.ac.ilpd.sharedlibrary.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserRequest 
{
    @NotBlank(message = "Names are required")
    @RestrictedString
    private String names;
    @RestrictedString
    private String email;
    @RestrictedString
    private String nationalIdPassport;
    @RestrictedString
    private String phone;
    @RestrictedString
    private String address;
    @RestrictedString
    private String tinNumber;
    @RestrictedString
    private String gender;
    @RestrictedString
    private String relationship;
    @RestrictedString
    private String sponsorType;
    @RestrictedString
    private String nationality;
    @RestrictedString
    private String institution;

    @NotBlank(message = "User type is required")
    @RestrictedString
    private String userType;
    @RestrictedString
    private String position;

}
