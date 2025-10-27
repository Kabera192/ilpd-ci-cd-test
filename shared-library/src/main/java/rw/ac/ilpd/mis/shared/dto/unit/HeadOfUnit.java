package rw.ac.ilpd.mis.shared.dto.unit;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeadOfUnit {
    private String id;

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Unit ID is required")
    private String unitId;

    @NotNull(message = "Created at is required")
    private String createdAt;

    @NotNull(message = "Is currently head flag is required")
    private Boolean isCurrentlyHead;
}
