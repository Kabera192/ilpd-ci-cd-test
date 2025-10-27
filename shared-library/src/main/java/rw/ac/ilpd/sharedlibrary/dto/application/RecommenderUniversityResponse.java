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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommenderUniversityResponse {
    private String id;
    private String recommenderId;
    private String academicBackgroundId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}