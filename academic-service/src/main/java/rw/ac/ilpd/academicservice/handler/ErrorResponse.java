package rw.ac.ilpd.academicservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
