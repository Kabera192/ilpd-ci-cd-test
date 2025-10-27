/**
 * ExternalUser.java
 *  
 * This DTO class is used to represent an external user of the MIS (a user without an account).
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified 2025-07-03
 */
package rw.ac.ilpd.mis.shared.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserRequest 
{
    @NotBlank(message = "Names are required")
    private String names;

    private String email;

    private String nationalIdPassport;

    private String phone;

    private String address;

    private String tinNumber;

    private String gender;

    private String relationship;

    private String sponsorType;

    private String nationality;

    private String institution;

    @NotBlank(message = "User type is required")
    private String userType;
    
    private String position;

}
