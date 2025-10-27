package rw.ac.ilpd.reportingservice.handler;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import rw.ac.ilpd.reportingservice.exception.BadRequestException;
import rw.ac.ilpd.reportingservice.exception.ConflictException;
import rw.ac.ilpd.reportingservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.sharedlibrary.dto.util.BulkResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        log.error("Handler method validation error: {}", ex.getMessage());
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Error");

        Map<String, String> validationErrors = new HashMap<>();
        ex.getAllValidationResults().forEach(result -> {
            String parameterName = result.getMethodParameter().getParameterName();
            result.getResolvableErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                validationErrors.put(parameterName, errorMessage);
            });
        });
        errors.put("details", validationErrors);
//        errors.put("timestamp", java.time.LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp)
    {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error ->
                {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException ex)
    {
        log.error("Bind exception: {}", ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Error");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->
        {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        errors.put("details", fieldErrors);
        errors.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(TypeMismatchException ex)
    {
        log.error("Type mismatch: {}", ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Error");
        errors.put("details", Map.of(ex.getPropertyName(), "Invalid format: " + ex.getMessage()));
//        errors.put("timestamp", java.time.LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex)
    {
        log.error(ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Error");

        Map<String, String> violationErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
        {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            violationErrors.put(field, message);
        });
        errors.put("details", violationErrors);
        errors.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex)
    {
        log.error(ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Data integrity violation");
        error.put("message", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityAlreadyExists ex)
    {

        Map<String, String> error = new HashMap<>();
        error.put("error", "Duplicate Entry");
        error.put("error", "Entity already exists");
        error.put("message", ex.getReason());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityNotFoundException ex)
    {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Entity Not Found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(IllegalStateException ex)
    {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Illegal State Exception");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(BadRequestException ex)
    {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(ConflictException ex)
    {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Conflict exception");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleDisabledException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        log.info("Exception {}",ex);
        error.put("error", "Something went wrong");
        error.put("message", "Contact our developer team for help");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex)
    {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BulkResponseException.class)
    public ResponseEntity<BulkResponse<?>> handleCustomServiceException(BulkResponseException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getBulkResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex)
    {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Server Error");
        error.put("message", "Contact our developer team for help");
        error.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


