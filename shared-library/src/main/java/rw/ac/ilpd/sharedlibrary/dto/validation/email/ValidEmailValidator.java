package rw.ac.ilpd.sharedlibrary.dto.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.IDN;
import java.util.regex.Pattern;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    // Pragmatic email pattern (covers most real-world emails)
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private boolean required;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) {
            return !required; // null/blank allowed unless required=true
        }

        // Split local and domain parts
        int at = value.lastIndexOf('@');
        if (at <= 0 || at == value.length() - 1) return false;

        String local = value.substring(0, at);
        String domain = value.substring(at + 1);

        // Basic checks
        if (local.length() > 64 || domain.length() > 255) return false;

        // Convert Unicode domain to ASCII (punycode) before regex
        String asciiDomain;
        try {
            asciiDomain = IDN.toASCII(domain, IDN.ALLOW_UNASSIGNED);
        } catch (Exception e) {
            return false;
        }

        String candidate = local + "@" + asciiDomain;

        // Final pattern test
        if (!EMAIL.matcher(candidate).matches()) return false;

        // No consecutive dots in local or domain, and no leading/trailing dots or hyphens
        if (local.startsWith(".") || local.endsWith(".") || local.contains("..")) return false;
        if (asciiDomain.startsWith("-") || asciiDomain.endsWith("-")) return false;
        if (asciiDomain.contains("..")) return false;

        return true;
    }
}
