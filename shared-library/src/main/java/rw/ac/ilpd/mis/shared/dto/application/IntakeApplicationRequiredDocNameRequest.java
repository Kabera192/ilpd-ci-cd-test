/*
 * IntakeApplicationRequiredDocNameDto.java
 * 
 * This class is used to represent an intake application required document name.
 * 
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeApplicationRequiredDocNameRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
