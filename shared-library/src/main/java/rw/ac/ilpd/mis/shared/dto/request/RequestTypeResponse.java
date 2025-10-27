/*
 * File: ResponseTypeResponse.java
 * 
 * Description: Data Transfer Object representing a request type. Contains information about the type of request, 
 *              including its name, deletion status, and whether it requires payment.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTypeResponse {
    private String id;
    private String name;
    private Boolean deletedStatus;
    private Boolean needsPayment;
    private List<RequestTypeRoleResponse> roles;
}