package rw.ac.ilpd.sharedlibrary.dto.validation.uuid.optional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class OptionalUuidValidator implements ConstraintValidator<OptionalValidUuid, String> {
    @Override
    public void initialize(OptionalValidUuid constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isBlank()) {
            try {
                UUID.fromString(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        } else {
            return true;
        }
    }
}