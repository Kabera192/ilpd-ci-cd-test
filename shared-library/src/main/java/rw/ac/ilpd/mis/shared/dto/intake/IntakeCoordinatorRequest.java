/*
 * File: IntakeCoordinatorRequest.java
 * 
 * Description: Data Transfer Object representing an Intake Coordinator assignment.
 *              Contains information about the coordinator assigned to an intake,
 *              including assignment period and creation timestamp.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.intake;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntakeCoordinatorRequest {

    @NotBlank(message = "Coordinator ID is required")
    private String coordinatorId;

    @NotBlank(message = "Intake ID is required")
    private String intakeId;

    @NotBlank(message = "From date is required")
    private String from;

    private String to;
}