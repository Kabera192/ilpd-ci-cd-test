package rw.ac.ilpd.academicservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.integration.client.UserClient;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
 private final UserClient  userClient;
    @SuppressWarnings("NullableProblems")
    @Override
    public Optional<String> getCurrentAuditor() {
//        Extract username
        String userName;
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth==null) {
            return Optional.of("SYSTEM");
        }
        userName=auth.getName();
        log.info("userName={}", userName);
        LinkedHashMap<String,Object> userByUserName = (LinkedHashMap<String, Object>) userClient.getByUsername(userName);
//      depends on key name
        log.info("userByUserName={}", userByUserName);
        if(userByUserName!=null){
            // Cast the main object to a Map
            Map<String, Object> userMap = (Map<String, Object>) userByUserName;
            // Get the result part
            Map<String, Object> result = (Map<String, Object>) userMap.get("result");
            String id = (String) result.get("id");
            return Optional.of(id);
        }else {
           return Optional.of("SYSTEM");
        }
    }
}
