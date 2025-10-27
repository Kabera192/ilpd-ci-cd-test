/*
 * File: RequestCommentRequest.java
 * 
 * Description: Data Transfer Object representing a comment on a request.
 *              Contains information about the comment content, the associated request,
 *              the user who created the comment, and the timestamp of creation.
 *              Validation constraints:
 *                - content: must not be null or blank
 *                - requestId: must not be null
 *                - createdBy: must not be null
 *                - createdAt: must not be null
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCommentRequest {

    @NotNull(message = "Content is required")
    @NotBlank(message = "Content cannot be blank")
    @RestrictedString
    private String content;

    @NotNull(message = "Created by user ID is required")
    private UUID createdBy;
}