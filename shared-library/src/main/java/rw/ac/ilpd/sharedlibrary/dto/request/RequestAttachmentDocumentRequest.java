/*
 * File: RequestAttachmentDocumentRequest.java
 * 
 * Description: Data Transfer Object representing an attachment document associated with a request.
 *              Contains identifiers for the attachment, the related request, and the document itself.
 *              Fields:
 *                - id: Unique identifier for the attachment document.
 *                - requestId: Unique identifier for the related request. Required.
 *                - documentId: Unique identifier for the document being attached. Required.
 *              Validation:
 *                - requestId and documentId must not be null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentRequest;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAttachmentDocumentRequest {

    @NotNull(message = "Request ID is required")
    private DocumentRequest document;
}