package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private String id;
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
    private String email;
    private boolean isActive;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
