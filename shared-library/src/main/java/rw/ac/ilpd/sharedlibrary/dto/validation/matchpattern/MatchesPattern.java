package rw.ac.ilpd.sharedlibrary.dto.validation.matchpattern;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatchesPatternValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesPattern {
    String message() default "Invalid value";

    /** Regex pattern to match against */
    String regexp() default "";

    /** Whether the field is required */
    boolean required() default false;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

