/*
 * IntakeApplicationRequiredDocNameDto.java
 * 
 * This class is used to represent an intake application required document name.
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
public class IntakeApplicationRequiredDocNameResponse {
    private String id;
    private String name;
}
