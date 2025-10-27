package rw.ac.ilpd.sharedlibrary.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RestrictedStringValidatorImpl implements ConstraintValidator<RestrictedString, String>
{
    // Regex: Allows letters, numbers, apostrophes ('), hyphens (-), underscores (_), and spaces
    //
    private static final String VALID_PATTERN = "^(?=.*[a-zA-Z].*[a-zA-Z])[a-zA-Z0-9-.\s]+$";

    @Override
    public void initialize(RestrictedString constraintAnnotation)
    {
        // No initialization needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if (value == null)
        {
            return true; // @NotBlank handle null checks
        }
        return value.matches(VALID_PATTERN);
    }
}
