/**
 * File: ApplicationSponsorDto.java
 * <p>
 * Description:
 * Data Transfer Object (DTO) representing a sponsor associated with an application.
 * This class is intended for transferring sponsor-related data between different layers
 * of the application.
 * <p>
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Since: 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSponsorRequest {

    @NotNull(message = "Sponsor ID cannot be null")
    @NotBlank(message = "Sponsor ID cannot be blank")
    private String sponsorId;

    @NotNull(message = "Application ID cannot be null")
    @NotBlank(message = "Application ID cannot be blank")
    private String applicationId;

    @NotNull(message = "Recommendation Letter Document ID cannot be null")
    @NotBlank(message = "Recommendation Letter Document ID cannot be blank")
    private String recommendationLetterDocId;
}