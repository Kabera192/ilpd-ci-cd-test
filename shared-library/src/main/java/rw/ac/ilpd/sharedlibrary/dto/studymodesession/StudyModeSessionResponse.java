/**
 * Response payload representing a study mode session in the ILPD system.
 *
 * <p>This DTO returns the session ID, name, linked study mode, starting and ending days, creation timestamp, and assessment group ID.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.studymodesession;

import lombok.*;

import java.time.DayOfWeek;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyModeSessionResponse {
    private String id;
    private String name;
    private String studyModeId;
    private DayOfWeek startingDay;
    private DayOfWeek endingDay;
    private String createdAt;
    private String assessmentGroupId;
}
