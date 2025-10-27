/*
 * File: DocumentDto.java
 * 
 * Description: A DTO that represents the documents that are handled in the MIS. This object has a url link to
 *              the actual document on the file server, a document type id and the time stamp of its creation.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentRequest {
    private String url;
    @NotBlank(message = "Type ID is required")
    private String typeId;
    @NotNull(message = "File to upload is required")
    private MultipartFile file;
}