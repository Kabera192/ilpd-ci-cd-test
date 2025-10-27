package rw.ac.ilpd.registrationservice.projection;

import rw.ac.ilpd.sharedlibrary.enums.DocumentVerificationStatus;

public interface DocumentStatusCount {
    DocumentVerificationStatus getId();

    Long getCount();
}
