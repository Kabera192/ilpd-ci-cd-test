/*
 * File: DocumentDto.java
 * 
 * Description: A DTO that represents the documents that are handled in the MIS. This object has a url link to
 *              the actual document on the file server, a document type id and the time stamp of its creation.
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
public class DocumentResponse {
    private String id;
    private String url;
    private String typeId;
    private String createdAt;
}