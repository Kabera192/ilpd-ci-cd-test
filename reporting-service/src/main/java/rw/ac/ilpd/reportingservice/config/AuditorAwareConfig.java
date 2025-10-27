package rw.ac.ilpd.reportingservice.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Example: fetch the username from Spring Security
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        TODO add feign client to fetch user id or find user in redis db
//        return Optional.ofNullable(name);
        return Optional.of("system-user");
    }
}
