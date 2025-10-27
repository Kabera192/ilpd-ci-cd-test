package rw.ac.ilpd.reportingservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
