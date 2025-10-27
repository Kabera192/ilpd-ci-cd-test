package rw.ac.ilpd.mis.shared.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalUser {

    private String id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "National ID is required")
    private String nationalId;

    @NotNull(message = "Phone is required")
    private String phone;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Gender is required")
    private String gender;

    private String relationship;

    private String sponsorType;

    @NotNull(message = "Nationality is required")
    private String nationality;

    private String institution;

    @NotNull(message = "User type is required")
    private String userType;

    private String position;
}
