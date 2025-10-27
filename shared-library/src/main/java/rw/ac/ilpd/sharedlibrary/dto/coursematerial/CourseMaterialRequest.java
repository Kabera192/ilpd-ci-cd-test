/**
 * This file defines the CourseMaterialRequest DTO used for handling course material data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.coursematerial;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseMaterialRequest{

    @NotBlank(message = "Bucket name is required")
    private String bucketName;

    @NotBlank(message = "Object path is required")
    private String objectPath;

    @NotBlank(message = "Title cannot be blank")
    @RestrictedString
    private String title;

    @RestrictedString
    private String description; // Optional field

    private MultipartFile attachment;
}
