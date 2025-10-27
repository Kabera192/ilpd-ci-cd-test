package rw.ac.ilpd.mis.shared.dto.user;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
    private String id;

    private String username;

    private String email;

    private String password;

    private String firstName;
    private LocalDate dateOfBirth;
    private String lastName;

    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;

    private String twoFactorSecret;
    private boolean isTwoFactorEnabled = false;
    private String signUpMethod;
    private List<String> roles;
    private List<String> permissions;
    private List<String> authorities;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String telephone;
    private String identityNumber;

    public List<String> getAuthorities() { return authorities; }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuth userAuth = (UserAuth) o;

        if (!id.equals(userAuth.id)) return false;
        if (!username.equals(userAuth.username)) return false;
        return email != null ? email.equals(userAuth.email) : userAuth.email == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

}