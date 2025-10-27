package rw.ac.ilpd.registrationservice.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;

import java.util.function.Supplier;

/**
 * Custom AuthorizationManager that grants access to users with the super_admin privilege,
 * while delegating to the standard authorization rules for other users.
 */
@Component
public class SuperAdminAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final AuthorizationManager<RequestAuthorizationContext> delegate;

    /**
     * Constructor that takes a delegate AuthorizationManager to handle non-superadmin authorization.
     *
     * @param delegate The delegate AuthorizationManager
     */
    public SuperAdminAuthorizationManager(AuthorizationManager<RequestAuthorizationContext> delegate) {
        this.delegate = delegate;
    }

    /**
     * Default constructor that creates a SuperAdminAuthorizationManager with no delegate.
     * This will only check for the super_admin privilege.
     */
    public SuperAdminAuthorizationManager() {
        this.delegate = null;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // Check if the user has the super_admin privilege
        boolean isSuperAdmin = authentication.get().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(SuperPrivilege.SUPER_ADMIN));

        // If the user is a superadmin, grant access
        if (isSuperAdmin) {
            return new AuthorizationDecision(true);
        }

        // If there's a delegate, use it to check authorization
        if (delegate != null) {
            return delegate.check(authentication, context);
        }

        // If there's no delegate, deny access to non-superadmins
        return new AuthorizationDecision(false);
    }
}