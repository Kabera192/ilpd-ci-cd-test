/**
 * This file defines the DeliberationRulesThresholdResponse DTO used for returning deliberation rules threshold data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.deliberationrulesthreshold;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRulesThresholdResponse {
    private String id;
    private String key;
    private Double value;
    private String createdAt;
}
