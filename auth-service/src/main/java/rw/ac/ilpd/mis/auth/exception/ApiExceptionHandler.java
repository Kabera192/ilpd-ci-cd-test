package rw.ac.ilpd.mis.auth.exception;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project misold
 * @date 08/08/2025
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class) // bad JSON / enum mismatch
    public ResponseEntity<?> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        var msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        return problem(HttpStatus.BAD_REQUEST, "Bad Request", msg, req, null);
    }

    @ExceptionHandler({ BindException.class, MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class })
    public ResponseEntity<?> handleBind(Exception ex, HttpServletRequest req) {
        return problem(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid @RequestBody
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<Map<String, Object>> details = ex.getBindingResult().getFieldErrors().stream()
                .map(this::detail).toList();
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation Failed",
                "One or more fields are invalid.", req, details);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
//
//        List<Map<String, String>> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("field", error.getField());
//                    map.put("message", error.getDefaultMessage());
//                    return map;
//                })
//                .toList();
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
//        body.put("errors", errors);
//
//        return ResponseEntity.badRequest().body(body);
//}

    @ExceptionHandler(ConstraintViolationException.class) // @Validated on params/path vars
    public ResponseEntity<?> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        var details = ex.getConstraintViolations().stream()
                .map(v -> Map.of("field", v.getPropertyPath().toString(),
                        "rejectedValue", v.getInvalidValue(),
                        "message", v.getMessage()))
                .toList();
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Validation Failed",
                "One or more fields are invalid.", req, details);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethod(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return problem(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", ex.getMessage(), req, null);
    }

    @ExceptionHandler(Exception.class) // last-resort 500
    public ResponseEntity<?> handleGeneric(Exception ex, HttpServletRequest req) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), req, null);
    }

    private Map<String, Object> detail(FieldError fe) {
        log.error("Field error: {}", fe.getField());
        return Map.of("field", fe.getField(),
                "rejectedValue", fe.getRejectedValue(),
                "message", fe.getDefaultMessage());
    }

    private ResponseEntity<Map<String, Object>> problem(HttpStatus status, String error,
                                                        String message, HttpServletRequest req,
                                                        List<?> details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", error != null ? error : status.getReasonPhrase());
        body.put("message", message != null ? message : "Unexpected error");
        String path = req != null ? req.getServletPath() : null;
        if (path != null) body.put("path", path);
        if (details != null && !details.isEmpty()) body.put("details", details);

        return ResponseEntity.status(status).body(body);
    }
}

