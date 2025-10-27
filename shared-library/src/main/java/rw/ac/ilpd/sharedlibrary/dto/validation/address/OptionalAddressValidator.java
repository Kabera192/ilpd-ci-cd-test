package rw.ac.ilpd.sharedlibrary.dto.validation.address;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionalAddressValidator implements ConstraintValidator<OptionalValidAddress, String> {

    private static final String ADDRESS_REGEX = "^[a-zA-Z0-9\\s,.'\\-/]{3,250}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return value.matches(ADDRESS_REGEX);
    }
}