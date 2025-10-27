/**
 * This file defines the CurriculumModuleResponse DTO used for returning curriculum module data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.curriculummodule;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumModuleResponse {
    private String id;
    private String curriculumId;
    private String moduleId;
    private Integer moduleOrder;
    private Integer credits;
}
