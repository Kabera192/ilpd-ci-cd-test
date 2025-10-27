/*
 * File: ResponseResponse.java
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


import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponse
{
    private String id;
    private String content;
    private String requestTypeId;
    private UUID createdBy;
    private RequestStatus status;
    private UUID intakeToId;
    private UUID intakeFromId;
    private UUID moduleId;
    private UUID intakeId;
    private Boolean isDeleted;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private List<RequestCommentRequest> comments;
    private List<RequestAttachmentDocumentRequest> attachmentDocuments;
    private List<UserRequestApprovalRequest> userApprovals;
}