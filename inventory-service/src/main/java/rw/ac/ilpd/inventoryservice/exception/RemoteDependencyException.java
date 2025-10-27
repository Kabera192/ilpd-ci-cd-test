package rw.ac.ilpd.inventoryservice.exception;
/**
 * Constructs a new RemoteDependencyException with the specified detail message and cause.
 *
 * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method).
 */
public class RemoteDependencyException extends RuntimeException {
    public RemoteDependencyException(String message)
    {
        super(message);
    }
}
