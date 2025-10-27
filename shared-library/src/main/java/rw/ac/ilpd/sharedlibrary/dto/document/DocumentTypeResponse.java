/*
 * File: DocumentTypeDto.java
 * 
 * Description: DTO that represents the various document types e.g. Templates, Attachments and so on.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.document;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentTypeResponse {
    private String id;
    private String name;
    private String path;
}