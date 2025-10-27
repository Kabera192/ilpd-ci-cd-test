package rw.ac.ilpd.reportingservice.exception;
/*
* Michel Igiraneza
* */
public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super( message);
    }
}
