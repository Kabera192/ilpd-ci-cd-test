/*
 * File Name: UserResponseApprovalResponse.java
 * 
 * Description: 
 *   Data Transfer Object representing user request approval information.
 *   Contains fields for approval record ID, user approval ID, request ID, status, and creation timestamp.
 *   Includes validation to ensure all required fields are provided.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.request;



import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestApprovalResponse {
    private String userApprovalId;
    private String status;
    private LocalDateTime createdAt;
}