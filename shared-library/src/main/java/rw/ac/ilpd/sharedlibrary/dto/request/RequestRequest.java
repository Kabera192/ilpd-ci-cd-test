/*
 * File: RequestRequest.java
 * 
 * Description: Data Transfer Object representing a request in the system.
 *              Encapsulates details such as content, type, creator, status, and metadata
 *              including creation and update timestamps. Validation annotations ensure
 *              required fields are provided and not blank.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.request;



import jakarta.validation.constraints.*;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRequest {

    @NotNull(message = "Content is required")
    @NotBlank(message = "Content cannot be blank")
    @RestrictedString
    private String content;

    @NotBlank(message = "Request type ID is required")
    @RestrictedString
    private String requestTypeId;

    @NotNull(message = "Created by user ID is required")
    private UUID createdBy;

    @NotNull(message = "Status is required")
    private RequestStatus status;

    private UUID intakeToId;
    private UUID intakeFromId;
    private UUID moduleId;
    private UUID intakeId;
    private List<RequestCommentRequest> comments;
    private List<RequestAttachmentDocumentRequest> attachmentDocuments;
    private List<UserRequestApprovalRequest> userApprovals;
}