/*
 * File Name: UserRequestApprovalRequest.java
 * 
 * Description: 
 *   Data Transfer Object representing user request approval information.
 *   Contains fields for approval record ID, user approval ID, request ID, status, and creation timestamp.
 *   Includes validation to ensure all required fields are provided.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.RequestApprovalStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestApprovalRequest {

    @NotBlank(message = "User approval ID is required")
    private String userApprovalId;

    @NotNull(message = "Status is required")
    private RequestApprovalStatus status;
}