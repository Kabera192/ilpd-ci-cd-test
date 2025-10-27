/**
 * This file defines the DeliberationRulesThresholdRequest DTO used for handling deliberation rules threshold data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.deliberationrulesthreshold;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRulesThresholdRequest {

    @NotBlank(message = "Key cannot be null or blank")
    @RestrictedString
    private String key;

    @NotNull(message = "Value cannot be null")
    private Double value;
}
