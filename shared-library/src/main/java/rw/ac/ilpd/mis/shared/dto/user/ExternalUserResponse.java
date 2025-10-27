/**
 * ExternalUser.java
 *  
 * This DTO class is used to represent an external user of the MIS (a user without an account).
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified 2025-07-03
 */
package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserResponse {

    private String id;
    private String name;
    private String email;
    private String nationalId;
    private String phone;
    private String address;
    private String city;
    private String gender;
    private String relationship;
    private String sponsorType;
    private String nationality;
    private String institution;
    private String userType;
    private String position;
    private String createdAt;
}
