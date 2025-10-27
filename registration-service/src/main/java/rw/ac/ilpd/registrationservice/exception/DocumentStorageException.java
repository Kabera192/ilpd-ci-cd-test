package rw.ac.ilpd.registrationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception thrown when document storage operations fail
 */
public class DocumentStorageException extends RuntimeException {
Logger logger = LoggerFactory.getLogger(DocumentStorageException.class);
    public DocumentStorageException(String message) {
        super(message);
    }

    public DocumentStorageException(String message, Throwable cause) {
        super(message, cause);
    }
    @ExceptionHandler(DocumentStorageException.class)
    public ResponseEntity<String> handleDocumentStorageException(DocumentStorageException ex) {
        logger.error("Document storage error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}