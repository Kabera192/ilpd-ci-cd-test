/*
 * File: IntakeCoordinatorResponse.java
 * 
 * Description: Data Transfer Object representing an Intake Coordinator assignment.
 *              Contains information about the coordinator assigned to an intake,
 *              including assignment period and creation timestamp.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.intake;



import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntakeCoordinatorResponse {
    private String id;
    private String coordinatorId;
    private String intakeId;
    private String from;

    private String to;
    private String createdAt;
}