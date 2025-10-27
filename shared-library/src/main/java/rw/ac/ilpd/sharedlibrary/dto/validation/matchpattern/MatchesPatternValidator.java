package rw.ac.ilpd.sharedlibrary.dto.validation.matchpattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MatchesPatternValidator implements ConstraintValidator<MatchesPattern, String> {

    private Pattern pattern;
    private boolean required;

    @Override
    public void initialize(MatchesPattern constraintAnnotation) {
        String regex = constraintAnnotation.regexp();
        this.required = constraintAnnotation.required();
        if (!regex.isEmpty()) {
            this.pattern = Pattern.compile(regex);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return !required; // valid if not required
        }
        if (pattern == null) {
            return true; // no pattern provided => accept any non-blank string
        }
        return pattern.matcher(value).matches();
    }
}
