package rw.ac.ilpd.registrationservice.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rw.ac.ilpd.registrationservice.exception.DocumentStorageException;
import rw.ac.ilpd.registrationservice.exception.DownstreamServiceException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the registration service.
 * Catches exceptions thrown by controllers and services and converts them to appropriate HTTP responses.
 * Now includes security exception handling for authentication and authorization errors.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles EntityNotFoundException, which is thrown when an entity is not found.
     * Returns a 404 NOT_FOUND response with the exception message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found: {}", ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles validation exceptions for request body validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation Failed");
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles constraint violation exceptions from JPA/MongoDB validation
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Constraint Violation");
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles data integrity violations (e.g., unique constraint violations)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation: {}", ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Data Integrity Violation");
        response.put("message", "A data constraint was violated. Please check your request.");
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles document storage exceptions
     * Thrown when document storage operations fail (e.g., invalid file type, file too large)
     */
    @ExceptionHandler(DocumentStorageException.class)
    public ResponseEntity<Map<String, Object>> handleDocumentStorageException(DocumentStorageException ex) {
        logger.error("Document storage exception: {}", ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Document Storage Error");
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ========== SECURITY EXCEPTION HANDLERS ==========

    /**
     * Handles Access Denied exceptions (403 Forbidden)
     * Thrown when authenticated user lacks required permissions
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Forbidden");
        response.put("message", "You do not have permission to access this resource");
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Handles Authentication exceptions (401 Unauthorized)
     * Thrown when authentication fails or is missing
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthentication(AuthenticationException ex) {
        logger.error("Authentication failed: {}", ex.getMessage());

        String message = "Authentication failed";
        if (ex instanceof BadCredentialsException) {
            message = "Invalid credentials provided";
        } else if (ex instanceof InsufficientAuthenticationException) {
            message = "Full authentication is required to access this resource";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Unauthorized");
        response.put("message", message);
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles all other runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        logger.error("Runtime exception occurred: ", ex);
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("timestamp", System.currentTimeMillis());

        // In development mode, include the actual error message
        String profile = System.getProperty("spring.profiles.active", "dev");
        if ("dev".equals(profile)) {
            response.put("debug", ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles general exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("Unexpected exception: ", ex);
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(DownstreamServiceException.class)
    public ResponseEntity<Object> handleDownstreamServiceException(DownstreamServiceException ex) {
        logger.error("Downstream service error from {}: HTTP {} - {}",
                ex.getServiceName(), ex.getHttpStatus(), ex.getResponseBody());

        // Check if response body is empty or null
        String responseBody = ex.getResponseBody();
        if (responseBody == null || responseBody.trim().isEmpty()) {
            // Create a proper error response when notification service doesn't provide one
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", getErrorMessageForStatus(ex.getHttpStatus()));
            errorBody.put("message", getMessageForStatus(ex.getHttpStatus()));
            errorBody.put("status", ex.getHttpStatus());
            errorBody.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(ex.getHttpStatus()).body(errorBody);
        }

        try {
            // Try to parse the response body as JSON and return it as-is
            ObjectMapper mapper = new ObjectMapper();
            JsonNode errorResponse = mapper.readTree(responseBody);

            return ResponseEntity.status(ex.getHttpStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        } catch (Exception parseException) {
            logger.warn("Failed to parse response body as JSON: {}", parseException.getMessage());

            // Fallback: create a structured response with the raw response body
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", getErrorMessageForStatus(ex.getHttpStatus()));
            errorBody.put("message", responseBody.trim().isEmpty() ? getMessageForStatus(ex.getHttpStatus()) : responseBody);
            errorBody.put("status", ex.getHttpStatus());
            errorBody.put("timestamp", System.currentTimeMillis());
            errorBody.put("service", ex.getServiceName());

            return ResponseEntity.status(ex.getHttpStatus()).body(errorBody);
        }
    }

    // Helper methods to provide appropriate messages for HTTP status codes
    private String getErrorMessageForStatus(int status) {
        return switch (status) {
            case 404 -> "Not Found";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 500 -> "Internal Server Error";
            default -> "Error";
        };
    }

    private String getMessageForStatus(int status) {
        return switch (status) {
            case 404 -> "Document with the specified ID was not found";
            case 400 -> "The request was invalid";
            case 401 -> "Authentication is required";
            case 403 -> "Access to this resource is forbidden";
            case 500 -> "An internal server error occurred";
            default -> "An error occurred while processing the request";
        };
    }}