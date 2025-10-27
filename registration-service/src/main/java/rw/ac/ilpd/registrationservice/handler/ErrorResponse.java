package rw.ac.ilpd.registrationservice.handler;

import java.util.Map;

/**
 * A record class that represents an error response.
 * Used to return structured error information in API responses.
 */
public record ErrorResponse(Map<String, String> errors) {
}