package rw.ac.ilpd.mis.auth.util;


import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapperFormatter {
    @Named("formatDate")
    public String formatDate(LocalDate dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return dateTime.format(formatter);
    }
    @Named("formatDateTime")
    public String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy  HH:mm a");
        return dateTime.format(formatter);
    }
}
