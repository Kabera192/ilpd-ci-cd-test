package rw.ac.ilpd.reportingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EvaluationFormTypeNotFoundException extends RuntimeException {
    public EvaluationFormTypeNotFoundException(String message) {
        super(message);
    }
}
