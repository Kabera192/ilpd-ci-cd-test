package rw.ac.ilpd.mis.auth.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
