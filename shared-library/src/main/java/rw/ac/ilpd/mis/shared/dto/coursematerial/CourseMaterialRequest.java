/**
 * This file defines the CourseMaterialRequest DTO used for handling course material data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.coursematerial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseMaterialRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description; // Optional field

    @NotBlank(message = "Document Type can not be blank")
    private String documentId;
}
