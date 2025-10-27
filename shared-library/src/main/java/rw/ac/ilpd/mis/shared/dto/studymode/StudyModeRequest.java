/**
 * Request payload for creating or updating a study mode in the ILPD system.
 *
 * <p>This DTO contains the name and acronym of the study mode.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.studymode;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyModeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @NotBlank(message = "Acronym cannot be null or blank")
    private String acronym;
}
