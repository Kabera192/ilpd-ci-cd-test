/**
 * Request payload for creating or updating a study mode session in the ILPD system.
 *
 * <p>This DTO includes the session name, linked study mode, starting and ending days, and assessment group.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.studymodesession;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyModeSessionRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;
    @NotBlank(message = "Study mode can not be empty")
    @NotBlank(message = "Study mode ID cannot be null or blank")
    private String studyModeId;

    @NotNull(message = "Starting day cannot be null")
    private DayOfWeek startingDay;
    @NotNull(message = "Ending day cannot be null")
    private DayOfWeek endingDay;
    @NotBlank(message = "Assessment group can not be empty")
    @NotBlank(message = "Assessment group ID cannot be null")
    private String assessmentGroupId;
}
