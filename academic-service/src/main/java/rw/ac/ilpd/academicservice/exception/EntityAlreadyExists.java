package rw.ac.ilpd.academicservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityAlreadyExists extends ResponseStatusException {
    public EntityAlreadyExists(String message) {
        super(HttpStatus.CONFLICT,  message);
    }
}
