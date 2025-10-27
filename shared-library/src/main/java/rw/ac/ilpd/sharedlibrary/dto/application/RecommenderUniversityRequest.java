/* RecommenderUniversityDto.java
 *
 * Represents a Data Transfer Object (DTO) for a university recommender.
 * This class encapsulates the relationship between a recommender and a specific academic background,
 * including unique identifiers for both entities. It is used to transfer data related to university
 * recommenders within the application, ensuring that both academic background and recommender IDs are provided.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
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
public class RecommenderUniversityRequest {

    @NotNull(message = "Recommender ID is required")
    @NotBlank(message = "Recommender ID cannot be blank")
    @RestrictedString
    private String recommenderId;

    // Optional - can be set automatically based on context
    @RestrictedString
    private String academicBackgroundId;
}