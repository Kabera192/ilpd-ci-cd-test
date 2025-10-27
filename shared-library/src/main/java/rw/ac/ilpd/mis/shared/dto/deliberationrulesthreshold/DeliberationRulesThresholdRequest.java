/**
 * This file defines the DeliberationRulesThresholdRequest DTO used for handling deliberation rules threshold data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.deliberationrulesthreshold;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRulesThresholdRequest {

    @NotBlank(message = "Key cannot be null or blank")
    private String key;

    @NotNull(message = "Value cannot be null")
    @Positive(message = "key must be positive")
    private Double value;
}
