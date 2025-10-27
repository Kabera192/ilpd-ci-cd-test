package rw.ac.ilpd.hostelservice.projection;

import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentMethodStats {
    PaymentMethod getId();
    Long getCount();
    BigDecimal getTotalAmount();
}