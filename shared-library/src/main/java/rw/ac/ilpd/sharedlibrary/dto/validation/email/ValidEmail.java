package rw.ac.ilpd.sharedlibrary.dto.validation.email;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidEmailValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "invalid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** If true, empty/null values are considered invalid (useful without @NotBlank). */
    boolean required() default false;
}
