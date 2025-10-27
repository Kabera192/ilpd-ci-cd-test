package rw.ac.ilpd.sharedlibrary.dto.planning;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;

import java.math.BigDecimal;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnualToDoStatusSummary {
    private ToDoStatus status;
    private Long count;
    private BigDecimal totalCost;
}
