package rw.ac.ilpd.registrationservice.projection;

import java.util.List;

public interface ApplicationSponsorCount {
    String getId();

    List<String> getSponsors();

    Long getCount();
}


