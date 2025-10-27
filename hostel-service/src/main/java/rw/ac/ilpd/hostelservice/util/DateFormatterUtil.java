package rw.ac.ilpd.hostelservice.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class DateFormatterUtil {
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm a");

    @Named("formatDateTime")
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(CUSTOM_FORMATTER) : null;
    }
}
