/**
 * This file defines the CurriculumRequest DTO used for handling curriculum data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.curriculum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumRequest {
    @NotBlank(message = "Name must not be blank")
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message = "Name must only contain letters, numbers, and spaces (no special characters)"
    )
    private String name;

    @NotBlank(message = "Description should not be null or blank")
    private String description;

    @NotBlank(message = "Status should not be null or blank")
    private String status; // "ACTIVE" or "RETIRED"

    private LocalDateTime endedAt;

    @NotBlank(message = "Program Id should not be null or blank")
    private String programId;

    public void setName(String name) {
        if (name != null) {
            name = name.trim().replaceAll("\\s{2,}", " ");
        }
        this.name = name;
    }
}
