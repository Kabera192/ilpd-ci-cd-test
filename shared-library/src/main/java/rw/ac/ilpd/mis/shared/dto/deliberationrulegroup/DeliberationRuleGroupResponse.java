/**
 * This file defines the DeliberationRuleGroupResponse DTO used for returning deliberation rule group data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.deliberationrulegroup;

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.deliberationrulegroupcurriculum.DeliberationRuleGroupCurriculumResponse;
import rw.ac.ilpd.mis.shared.dto.deliberationrulesthreshold.DeliberationRulesThresholdResponse;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRuleGroupResponse {
    private String id;
    private String name;
    private String status; // Using enum
    private String createdAt;
    List<DeliberationRuleGroupCurriculumResponse> curriculumList;
    List<DeliberationRulesThresholdResponse> thresholdList;
}
