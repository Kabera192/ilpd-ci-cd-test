package rw.ac.ilpd.sharedlibrary.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RestrictedStringValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestrictedString
{
    String message() default "String contains invalid characters. The characters: @, #, $, ^, *, |, \\, /, >, <" +
            " are not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
