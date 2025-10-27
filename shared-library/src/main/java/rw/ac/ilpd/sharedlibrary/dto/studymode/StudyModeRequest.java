/**
 * Request payload for creating or updating a study mode in the ILPD system.
 *
 * <p>This DTO contains the name and acronym of the study mode.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.studymode;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyModeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    private String name;

    @NotBlank(message = "Acronym cannot be null or blank")
    @RestrictedString
    private String acronym;
}
