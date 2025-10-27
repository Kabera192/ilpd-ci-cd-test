/*
 * File: RequestTypeRequest.java
 * 
 * Description: Data Transfer Object representing a request type. Contains information about the type of request, 
 *              including its name, deletion status, and whether it requires payment.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTypeRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @RestrictedString
    private String name;

    @NotNull(message = "Needs payment flag is required")
    private Boolean needsPayment;

    @NotNull(message = "request type roles are required")
    private List<RequestTypeRoleRequest> roles;
}