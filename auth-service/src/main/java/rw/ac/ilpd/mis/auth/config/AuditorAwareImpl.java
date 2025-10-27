package rw.ac.ilpd.mis.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.auth.service.UserService;
import rw.ac.ilpd.mis.shared.dto.user.User;
import java.util.Optional;

@Component("auditorProvider")
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
    private final UserService userService;
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
        User userByUserName =userService.findByUsername(userName);
//      depends on key name
        log.info("userByUserName={}", userByUserName);
        if(userByUserName!=null){
            return Optional.of(userByUserName.getId().toString());
        }else {
           return Optional.of("SYSTEM");
        }
    }
}
