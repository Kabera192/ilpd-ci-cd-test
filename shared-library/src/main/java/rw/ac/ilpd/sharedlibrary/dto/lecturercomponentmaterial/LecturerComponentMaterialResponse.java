/**
 * LecturerComponentMaterialResponse DTO.
 * Represents the response payload containing details of a course material linked to a lecturer's component.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerComponentMaterialResponse {
    private String id;
    private CourseMaterialResponse courseMaterial;
    private LecturerComponentResponse lecturerComponent;
}
