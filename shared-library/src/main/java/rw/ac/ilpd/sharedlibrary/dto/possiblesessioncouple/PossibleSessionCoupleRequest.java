/**
 * Request payload for defining a possible session couple in the ILPD system.
 *
 * <p>This DTO includes two session references.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple;

import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PossibleSessionCoupleRequest {

    @NotNull(message = "Session 1 ID cannot be null")
    private UUID session1Id;

    @NotNull(message = "Session 2 ID cannot be null")
    private UUID session2Id;

    
}
