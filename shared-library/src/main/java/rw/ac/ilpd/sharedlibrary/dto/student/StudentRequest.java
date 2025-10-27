/*
 * File: StudentRequest.java
 * 
 * Description: Data Transfer Object representing a Student.
 *              Contains information about a student including their unique identifier,
 *              associated user ID, registration number, and creation timestamp.
 *              Validation constraints:
 *                - userId: must not be null.
 *                - registrationNumber: must not be null, blank, and cannot exceed 50 characters.
 *                - createdAt: must not be null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.student;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {

    @NotBlank(message = "User ID is required")
    @RestrictedString
    private String userId;

    @NotNull(message = "Registration number is required")
    @NotBlank(message = "Registration number cannot be blank")
    @Size(message = "Registration number cannot exceed 50 characters")
    @RestrictedString
    private String registrationNumber;
}