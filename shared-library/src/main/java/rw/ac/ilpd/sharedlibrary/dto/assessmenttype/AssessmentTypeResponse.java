/**
 * This file defines the AssessmentTypeResponse DTO used for returning assessment type data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmenttype;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentTypeResponse {
    private String id;
    private String title;
    private String acronym;
    private BigDecimal weight;
    private String assessmentGroupId;
    private String createdAt;
    private boolean isDeleted;
}
