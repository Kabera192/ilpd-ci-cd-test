/*
 * IntakeApplicationRequiredDocDto.java
 * 
 * This class is used to represent an intake application required document.
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

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntakeApplicationRequiredDocRequest {

    @NotBlank(message = "Intake ID is required")
    private String intakeId;

    @NotNull(message = "You have to provide the document name id and whether it is required or not")
    private List<IntakeApplicationRequiredDocEmbed> requiredDocument = new ArrayList<>();
}
