/**
 * This file defines the AssessmentAttachmentRequest DTO used for submitting
 * document links associated with module component assessments.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmentattachment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentAttachmentRequest {

    @NotBlank(message = "Module component assessment ID cannot be null")
    @RestrictedString
    private String moduleComponentAssessmentId;

    @NotBlank(message = "Document ID cannot be null")
    @RestrictedString
    private String documentId;
}
