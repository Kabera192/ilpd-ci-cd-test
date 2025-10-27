package rw.ac.ilpd.sharedlibrary.dto.validation.uuid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UuidValidator implements ConstraintValidator<ValidUuid, String> {
    // Regex: Allows letters, numbers, apostrophes ('), hyphens (-), underscores (_), and spaces
    //

    @Override
    public void initialize(ValidUuid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}