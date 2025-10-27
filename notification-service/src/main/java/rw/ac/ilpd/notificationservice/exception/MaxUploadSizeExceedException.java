package rw.ac.ilpd.notificationservice.exception;

public class MaxUploadSizeExceedException  extends RuntimeException {
    public MaxUploadSizeExceedException(String message)
    {
        super(message);
    }

}
