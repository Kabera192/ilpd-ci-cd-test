/**
 * This file defines the DeliberationDistinctionResponse DTO used for returning deliberation distinction data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.deliberationdistinction;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationDistinctionResponse {
    private String id;
    private String name;
    private BigDecimal minScore;
    private BigDecimal maxScore;
}
