package rw.ac.ilpd.inventoryservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
