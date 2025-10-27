package rw.ac.ilpd.hostelservice.projection;

import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;

public interface PaymentStatusStats {
    PaymentStatus getId();
    Long getCount();
    BigDecimal getTotalAmount();
}