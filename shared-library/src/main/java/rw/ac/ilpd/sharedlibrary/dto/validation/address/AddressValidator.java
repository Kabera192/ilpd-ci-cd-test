package rw.ac.ilpd.sharedlibrary.dto.validation.address;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    private static final String ADDRESS_REGEX  = "^[a-zA-Z0-9\\s,.'\\-/]{3,250}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(ADDRESS_REGEX);
    }
}
