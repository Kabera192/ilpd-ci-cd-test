/**
 * This file defines the InstallmentModuleRequest DTO used for associating installments with curriculum modules.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.installmentmodule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentModuleRequest {

    @NotBlank(message = "Installment ID cannot be null or blank")
    @RestrictedString
    private String installmentId;

    @NotBlank(message = "Curriculum module ID cannot be null or blank")
    @RestrictedString
    private String curriculumModuleId;
}
