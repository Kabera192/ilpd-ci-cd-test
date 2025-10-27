package rw.ac.ilpd.registrationservice.projection;

import java.util.List;

public interface SponsorApplicationCount {
    String getId();

    List<String> getApplications();

    Long getCount();
}
