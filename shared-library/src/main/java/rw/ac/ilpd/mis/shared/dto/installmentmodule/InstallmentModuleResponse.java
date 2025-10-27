/**
 * This file defines the InstallmentModuleResponse DTO used for returning associations between installments and curriculum modules.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.installmentmodule;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentModuleResponse {
    private String id;
    private String installmentId;
    private String curriculumModuleId;
}
