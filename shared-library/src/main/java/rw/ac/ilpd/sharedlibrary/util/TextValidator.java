package rw.ac.ilpd.sharedlibrary.util;

import java.util.UUID;

public class TextValidator {
    public UUID uuidValidator(String uuid) {
        if (uuid == null) throw new IllegalArgumentException("uuid cannot be null");

        try {
           return UUID.fromString(uuid);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
