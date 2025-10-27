/**
 * DTO for Activity Level Response
 * This class represents the response data structure for activity level operations.
 *
 * @author Mohamed Gaye
 * @lastChangedDate 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activitylevel;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.LevelEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLevelResponse {
    private String id;
    private String level;
    private String levelRefId;
}