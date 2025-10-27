/*
 * File: ResponseCommentResponse.java
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



import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCommentResponse {
    private String content;
    private UUID createdBy;
    private LocalDateTime createdAt;
}