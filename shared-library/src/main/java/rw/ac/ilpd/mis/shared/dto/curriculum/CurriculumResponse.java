/**
 * This file defines the CurriculumResponse DTO used for returning curriculum data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.curriculum;

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.module.ModuleResponse;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumResponse {
    private String id;
    private String name;
    private String description;
    private String status;
    private String endedAt;
    private String programId;
    private String createdBy;
    private String createdAt;
    private List<ModuleResponse> modules;
}
