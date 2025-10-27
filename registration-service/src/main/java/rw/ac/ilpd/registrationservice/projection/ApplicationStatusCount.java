package rw.ac.ilpd.registrationservice.projection;

import rw.ac.ilpd.sharedlibrary.enums.ApplicationStatus;

public interface ApplicationStatusCount {
    ApplicationStatus getId();

    Long getCount();
}
