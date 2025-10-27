package rw.ac.ilpd.inventoryservice.handler;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rw.ac.ilpd.inventoryservice.exception.BadRequestException;
import rw.ac.ilpd.inventoryservice.exception.ConflictException;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp){
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError)error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<Map<String, Object>> handlePropertyValueException(PropertyValueException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Missing required field");
        error.put("field", ex.getPropertyName());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Validation failed");
        error.put("details", ex.getConstraintViolations());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Data integrity violation");
        error.put("message", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityAlreadyExists ex) {
            
        Map<String, String> error = new HashMap<>();
        error.put("error", "Entity already exists");
        error.put("message", ex.getReason());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Entity Not Found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(IllegalStateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Illegal State Exception");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(BadRequestException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Bad request");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(ConflictException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Conflict exception");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleDisabledException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Runtime exception");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "Unhandled exception");
//        error.put("message", ex.getClass().getSimpleName() + ": " + ex.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}


