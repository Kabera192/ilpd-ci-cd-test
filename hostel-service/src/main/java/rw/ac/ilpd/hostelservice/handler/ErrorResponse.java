package rw.ac.ilpd.hostelservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
