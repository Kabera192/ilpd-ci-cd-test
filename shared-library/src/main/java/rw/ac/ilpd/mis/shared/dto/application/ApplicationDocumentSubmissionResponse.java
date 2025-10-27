/*
 * ApplicationDocumentSubmissionDto.java
 *
 * This class is used to represent an application document submission.
 *
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDocumentSubmissionResponse {
    private String id;
    private String applicationId;
    private String documentId;
    private String requiredDocNameId;
    private String docVerificationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private List<ApplicationDeferringCommentResponse> comments;
    private int commentCount;
}
