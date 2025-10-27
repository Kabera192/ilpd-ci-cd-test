package rw.ac.ilpd.hostelservice.projection;

import java.math.BigDecimal;

public interface MonthlyStatsProjection {
    MonthlyStatsId getId();

    Long getCount();

    BigDecimal getTotalAmount();

    Long getCompletedCount();

    BigDecimal getCompletedAmount();
}