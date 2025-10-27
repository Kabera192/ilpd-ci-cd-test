/**
 * LecturerComponentMaterialRequest DTO.
 * Represents the request payload to link a course material with a lecturer's component.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.lecturercomponentmaterial;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerComponentMaterialRequest {

    @NotNull(message = "Course material ID cannot be null")
    private UUID courseMaterialId;

    @NotNull(message = "Lecturer component ID cannot be null")
    private UUID lecturerComponentId;
}
