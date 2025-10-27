/*
 * ApplicationDocumentSubmissionDto.java
 *
 * This class is used to represent an application document submission.
 *
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDocumentSubmissionRequest {

    @NotBlank(message = "Application ID cannot be blank")
    private String applicationId;

    @NotBlank(message = "Document ID cannot be blank")
    private String documentId;

    @NotBlank(message = "Required Doc Name ID cannot be blank")
    private String requiredDocNameId;

    @NotBlank(message = "Document Verification Status is required")
    private String docVerificationStatus;

    private String createdAt;
}
