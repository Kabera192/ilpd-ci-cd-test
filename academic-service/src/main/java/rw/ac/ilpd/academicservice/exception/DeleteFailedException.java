package rw.ac.ilpd.academicservice.exception;

public class DeleteFailedException extends RuntimeException {

    public DeleteFailedException(String message) {
        super(message);
    }

    public DeleteFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
