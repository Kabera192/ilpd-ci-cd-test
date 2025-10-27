/*
 * File: DocumentTypeDto.java
 * 
 * Description: DTO that represents the various document types e.g. Templates, Attachments and so on.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentTypeRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Path to store document of this type is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9/_-]+$",
            message = "Only letters, digits, underscores (_), hyphens (-), and forward slashes (/) are allowed"
    )
    private String path;
}