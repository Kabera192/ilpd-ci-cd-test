/*
 * IntakeApplicationRequiredDocDto.java
 * 
 * This class is used to represent an intake application required document.
 * 
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.sharedlibrary.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeApplicationRequiredDocResponse {

    private String id;
    private String intakeId;
    private String requiredDocNameId;
    private boolean isRequired;
}
