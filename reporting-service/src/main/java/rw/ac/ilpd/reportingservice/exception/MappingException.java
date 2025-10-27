package rw.ac.ilpd.reportingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler for the exceptions that occur during the mapping of
 * objects from one type to another.
 * */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MappingException extends RuntimeException
{
    public MappingException(String message)
    {
        super(message);
    }

    public MappingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
