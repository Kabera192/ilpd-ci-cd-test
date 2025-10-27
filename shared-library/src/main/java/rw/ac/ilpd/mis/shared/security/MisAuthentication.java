package rw.ac.ilpd.mis.shared.security;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */

import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class MisAuthentication implements Authentication {

    Principal principal;
    String credentials;
    List<String> roles;
    List<String> permissions;
    List<GrantedAuthority> authorities;
    boolean secure;
    String details;
    boolean isAuthenticated;
    UserAuth userAuth;

    public MisAuthentication(UserAuth userAuth, boolean secure, String authHeader) {
        this.principal = new MisPrincipal(userAuth.getUsername());
        this.credentials = userAuth.getPassword();
        this.roles = userAuth.getRoles();
        this.permissions = userAuth.getPermissions();
        this.authorities = new LinkedList<>();
        if( userAuth.getRoles() != null){
            for (String role : userAuth.getRoles()) {
                this.authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        if(userAuth.getPermissions() != null){
            for (String permission : userAuth.getPermissions()) {
                this.authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        this.secure = secure;
        this.details = authHeader;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthenticated = b;
    }

    @Override
    public String getName() {
        return principal.getName();
    }

    public boolean isSecure() {
        return secure;
    }

}
