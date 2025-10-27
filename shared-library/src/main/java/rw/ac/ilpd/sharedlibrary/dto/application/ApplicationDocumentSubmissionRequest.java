/*
 * ApplicationDocumentSubmissionDto.java
 *
 * This class is used to represent an application document submission.
 *
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDocumentSubmissionRequest {

    @NotBlank(message = "Application ID cannot be blank")
    @RestrictedString
    private String applicationId;

    @NotBlank(message = "Document ID cannot be blank")
    @RestrictedString
    private String documentId;

    @NotBlank(message = "Required Doc Name ID cannot be blank")
    @RestrictedString
    private String requiredDocNameId;

    @NotBlank(message = "Document Verification Status is required")
    @RestrictedString
    private String docVerificationStatus;
    @RestrictedString
    private String createdAt;
}
