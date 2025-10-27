/**
 * Response payload representing a possible session couple in the ILPD system.
 *
 * <p>This DTO returns details such as the session couple's ID and the linked sessions.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PossibleSessionCoupleResponse {
    private String id;
    private StudyModeSessionResponse session1Id;
    private StudyModeSessionResponse session2Id;
    private Boolean deletedStatus;
}
