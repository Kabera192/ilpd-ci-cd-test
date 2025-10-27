package rw.ac.ilpd.reportingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityAlreadyExists extends ResponseStatusException {
    public EntityAlreadyExists(String message) {
        super(HttpStatus.CONFLICT,  message);
    }
}
