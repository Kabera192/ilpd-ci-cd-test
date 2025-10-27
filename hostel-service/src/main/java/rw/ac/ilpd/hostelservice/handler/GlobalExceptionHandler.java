package rw.ac.ilpd.hostelservice.handler;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
import rw.ac.ilpd.hostelservice.exception.RoomCapacityExceededException;
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

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityExistsException ex) {
            
        Map<String, String> error = new HashMap<>();
        error.put("error", "Information already exists");
        error.put("message", ex.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDisabledException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Information Not Found");
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

    @ExceptionHandler(RoomCapacityExceededException.class)
    public ResponseEntity<?> handleRoomCapacityExceededException(RoomCapacityExceededException ex, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Room Capacity Exceeded");
        error.put("message", ex.getMessage());
        log.error(ex.getMessage(), request.getRequestURI(), ex);
//        error.put("path ", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unhandled exception");
        error.put("message", "Something went wrong");
//        error.put("message", ex.getClass().getSimpleName() + ": " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}


