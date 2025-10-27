/*
 * File: AcademicBackgroundDto.java
 *
 * Description: Data Transfer Object representing the academic background of an applicant.
 *              Contains information about the university attended, degree obtained, and the duration of study.
 *              Includes validation constraints for required fields and date logic.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicBackgroundResponse {
    private String id;
    private String applicationId;
    private String degree;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // Nested objects for rich responses
    private UniversityResponse university;
    private List<RecommenderUniversityResponse> recommenders;
}