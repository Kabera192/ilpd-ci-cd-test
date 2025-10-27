/**
 * This file defines the DeliberationDistinctionRequest DTO used for handling deliberation distinction data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationDistinctionRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    private String name;

    @NotNull(message = "Minimum score cannot be null")
    private BigDecimal minScore;

    @NotNull(message = "Maximum score cannot be null")
    private BigDecimal maxScore;
}
