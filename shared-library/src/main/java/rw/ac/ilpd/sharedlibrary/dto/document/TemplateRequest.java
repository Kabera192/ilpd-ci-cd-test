/*
 * File: TemplateDto.java
 * 
 * Description: Data Transfer Object that represents a document template in the MIS.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.document;

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
public class TemplateRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @RestrictedString
    private String name;

    @NotNull(message = "Document ID is required")
    private DocumentRequest document;

    @NotNull(message = "Is active flag is required")
    private Boolean isActive;
}