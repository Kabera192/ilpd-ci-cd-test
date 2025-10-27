package rw.ac.ilpd.reportingservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextJwtInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() instanceof String) {
            String token = (String) authentication.getCredentials();
            template.header("Authorization", "Bearer " + token);
        }
//
//        // Or if using JwtAuthenticationToken
//        if (authentication instanceof JwtAuthenticationToken) {
//            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
//            String token = jwtAuth.getToken().getTokenValue();
//            template.header("Authorization", "Bearer " + token);
//        }
    }
}