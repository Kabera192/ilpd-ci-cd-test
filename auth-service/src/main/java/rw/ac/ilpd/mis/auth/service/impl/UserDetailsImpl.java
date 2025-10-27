package rw.ac.ilpd.mis.auth.service.impl;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2024
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private boolean is2faEnabled;
    private String signUpMethod;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, String username, String email, String password,
                           boolean is2faEnabled, String signUpMethod,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.is2faEnabled = is2faEnabled;
        this.signUpMethod = signUpMethod;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserEntity userEntity) {

        return new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isTwoFactorEnabled(),
                userEntity.getSignUpMethod(),
                buildScope(userEntity) // Wrapping the roles and permissions in a list
        );
    }

    public static List<GrantedAuthority> buildScope(UserEntity userEntity) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Set<RoleEntity> roleEntities = new HashSet<>();
        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            roleEntities = userEntity.getRoles();
            roleEntities.forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
                if (!CollectionUtils.isEmpty(role.getPrivileges()))
                    role.getPrivileges().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
            });
        }

        if (!CollectionUtils.isEmpty(userEntity.getPrivileges())){
                userEntity.getPrivileges().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        }

        return authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean is2faEnabled() {
        return is2faEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public String getSignUpMethod() {
        return signUpMethod;
    }

    public void setSignUpMethod(String signUpMethod) {
        this.signUpMethod = signUpMethod;
    }
}