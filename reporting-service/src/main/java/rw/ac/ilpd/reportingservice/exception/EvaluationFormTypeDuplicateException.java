package rw.ac.ilpd.reportingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EvaluationFormTypeDuplicateException extends RuntimeException {
    public EvaluationFormTypeDuplicateException(String message) {
        super(message);
    }
}