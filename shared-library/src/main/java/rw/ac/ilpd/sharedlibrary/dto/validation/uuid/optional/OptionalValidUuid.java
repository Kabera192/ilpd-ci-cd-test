package rw.ac.ilpd.sharedlibrary.dto.validation.uuid.optional;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = OptionalUuidValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalValidUuid
{
    String message() default "Wrong provided uuid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
