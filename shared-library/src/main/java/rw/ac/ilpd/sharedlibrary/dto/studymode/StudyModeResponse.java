/**
 * Response payload representing a study mode entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, name, acronym, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.studymode;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyModeResponse {
    private String id;
    private String name;
    private String acronym;
    private String createdAt;
}
