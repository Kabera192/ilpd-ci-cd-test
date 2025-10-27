package rw.ac.ilpd.reportingservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Automatically returns HTTP 409
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}