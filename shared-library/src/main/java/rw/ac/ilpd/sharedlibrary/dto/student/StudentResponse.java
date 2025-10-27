/*
 * File: StudentResponse.java
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



import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private String id;
    private String userId;
    private String registrationNumber;
    private String createdAt;
}