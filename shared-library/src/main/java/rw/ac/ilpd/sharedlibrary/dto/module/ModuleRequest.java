/**
 * Request payload for creating or updating a module in the ILPD system.
 *
 * <p>This DTO includes details such as name, code, order, type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.module;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.enums.ModuleType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRequest {
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[^0-9.$!@#%^&*()+=?<>:;\"'`~\\\\|,\\[\\]{}]+$",
            message = "Invalid characters found. Only _ / - and space are allowed (no numbers, dots, or other special chars).")
    @Size(max = 60, message = "Module name must not exceed 60 characters")
    private String name;

    @NotBlank(message = "Code cannot be blank")
    @Pattern(
            regexp = "^[A-Z]{3} \\d{5}$",
            message = "Code must be three uppercase letters, a space, and exactly five digits. Example: DLP 91101"
    )
    @Pattern(
            regexp = "^[A-Z]{3} 911\\d{2}$",
            message = "Invalid generated code \n 9: stands for Post grasuate which is constant \n 1: Year of post graduate whiich is 1 and 1: stand  for trimester yet we have one \n other is module number which can vary   DLP 91101"
    )
    @NotBlank(message = "Code cannot be blank")
    @Pattern(
            regexp = "^[A-Z]{3} 911\\d{2}$",
            message = "Invalid generated code.\n\n" +
                    "Format: AAA 911XX\n" +
                    "- AAA : Three uppercase letters (e.g., DLP)\n" +
                    "- 9   : Constant (represents Postgraduate)\n" +
                    "- 1   : Year of postgraduate (constant = 1)\n" +
                    "- 1   : Trimester (constant = 1)\n" +
                    "- XX  : Module number (two digits that can vary)\n\n" +
                    "Example: DLP 91101"
    )
    private String code;

    @NotBlank(message = "Module type cannot be blank")
    @Pattern(regexp = "^(MOOT_COURT|INTERNSHIP|MODULE)$",
            message = "Module type must be one of: MOOT_COURT, INTERNSHIP, MODULE")
    private String type;

}
