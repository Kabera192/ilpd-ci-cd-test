/*
 * IntakeApplicationRequiredDocNameDto.java
 * 
 * This class is used to represent an intake application required document name.
 * 
 * @author Kabera Clapton (ckabera6@gmail.com)
 * @since 2025-07-04
 */
package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeApplicationRequiredDocNameRequest {
    @NotBlank(message = "Name is required")
    @RestrictedString
    private String name;
}
