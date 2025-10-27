package rw.ac.ilpd.sharedlibrary.dto.validation.address;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionalAddressValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalValidAddress {
    String message() default "Invalid address format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}