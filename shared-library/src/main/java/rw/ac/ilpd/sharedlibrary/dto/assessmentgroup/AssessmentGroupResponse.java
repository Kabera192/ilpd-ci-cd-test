/**
 * This file defines the AssessmentGroupResponse DTO used to represent
 * the response structure for an assessment group.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmentgroup;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentGroupResponse {
    private String id;
    private String name;
}
