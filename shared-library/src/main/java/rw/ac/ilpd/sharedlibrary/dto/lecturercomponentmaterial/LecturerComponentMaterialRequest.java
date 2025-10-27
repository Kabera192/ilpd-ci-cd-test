/**
 * LecturerComponentMaterialRequest DTO.
 * Represents the request payload to link a course material with a lecturer's component.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerComponentMaterialRequest {
//
//    @NotNull(message = "Course material ID cannot be null")
//    private UUID courseMaterialId;

    @NotBlank(message = "Title cannot be blank")
    @RestrictedString
    private String title;

    @NotBlank(message = "Description can not be empty")
    private String description; // Optional field

    private MultipartFile attachment;

    @NotBlank(message = "Lecturer component is required")
    @ValidUuid(message = "Invalid lecturer component format")
    private String lecturerComponentId;
}
