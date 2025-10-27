package rw.ac.ilpd.mis.shared.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;


/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */
public class MisSecurityContext implements SecurityContext {

    Authentication authentication;

    public MisSecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

}
