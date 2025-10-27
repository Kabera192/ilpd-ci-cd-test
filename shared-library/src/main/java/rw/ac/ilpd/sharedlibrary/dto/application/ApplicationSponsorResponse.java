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
package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationSponsorResponse {
    private String id;
    private String sponsorId;
    private String applicationId;
    private String recommendationLetterDocId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}