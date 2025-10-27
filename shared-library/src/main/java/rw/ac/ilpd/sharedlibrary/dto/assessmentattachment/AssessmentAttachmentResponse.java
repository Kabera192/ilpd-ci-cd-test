/**
 * This file defines the AssessmentAttachmentResponse DTO used to represent
 * the response structure for document attachments linked to assessments.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmentattachment;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentAttachmentResponse {
    private String id;
    private String moduleComponentAssessmentId;
    private String documentId;
}
