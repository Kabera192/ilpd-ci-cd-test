package rw.ac.ilpd.notificationservice.config;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rw.ac.ilpd.notificationservice.exception.MaxUploadSizeExceedException;
import rw.ac.ilpd.notificationservice.exception.RemoteDependencyException;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
     @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status",HttpStatus.NOT_FOUND.value());
        errors.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
        errors.put("message",ex.getMessage());
        errors.put("path",request.getRequestURI());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({RemoteDependencyException.class})
    public ResponseEntity<Object> remoteDependencyHandler(RemoteDependencyException ex, HttpServletRequest request) {
       log.error("",ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status",HttpStatus.SERVICE_UNAVAILABLE.value(),
                        "error",HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                        "message",ex.getMessage(),
                        "path",request.getRequestURI()
                ));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEntityExistsException(EntityExistsException ex,HttpServletRequest request) {
        log.error("",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status",HttpStatus.BAD_REQUEST.value(),
                        "error",HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "message",ex.getMessage(),
                        "path",request.getRequestURI()
                ));
    }
    @ExceptionHandler(MaxUploadSizeExceedException.class)
    public ResponseEntity<Map<String, Object>> handleEntityExistsException(MaxUploadSizeExceedException ex, HttpServletRequest request) {
        log.error("",ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status",HttpStatus.BAD_REQUEST.value(),
                        "error",HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "message",ex.getMessage(),
                        "path",request.getRequestURI()
                ));
    }

}
