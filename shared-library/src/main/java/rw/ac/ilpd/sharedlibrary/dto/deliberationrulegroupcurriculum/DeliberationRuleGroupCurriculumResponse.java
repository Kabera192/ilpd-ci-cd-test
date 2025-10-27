/**
 * This file defines the DeliberationRuleGroupCurriculumResponse DTO used for returning deliberation rule group curriculum data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRuleGroupCurriculumResponse {
    private String id;
    private String curriculumId;
    private String status;
    private String createdAt;
}
