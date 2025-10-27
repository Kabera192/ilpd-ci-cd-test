package rw.ac.ilpd.mis.shared.security;


/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Get the resource method which matches with the requested URL
        // Extract the privileges declared by it
        if (!(handler instanceof HandlerMethod)) {
            logger.debug("Method {} is not defined or implemented", handler.toString());
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.getWriter().write("Method not defined neither implemented");
            return false;

        }
        Method resourceMethod = ((HandlerMethod) handler).getMethod();

        List<String> allowedPermissions = extractPermissions(resourceMethod);
        logger.debug("Allowed method privileges for method {} are {}",
                resourceMethod.getName(),
                allowedPermissions.isEmpty() ? "None" : Arrays.toString(allowedPermissions.toArray()));

        if (allowedPermissions.isEmpty()) {
            // Get the resource class which matches with the requested URL
            // Extract the privileges declared by it
            Class<?> resourceClass = resourceMethod.getDeclaringClass();
            allowedPermissions = extractPermissions(resourceClass);
            logger.debug("Allowed class privileges for class " + resourceClass.getName()
                    + " are " + (allowedPermissions.isEmpty() ? "None" : Arrays.toString(allowedPermissions.toArray())));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!allowedPermissions.isEmpty() && !checkPermissions(allowedPermissions)) {
            logger.info("User "+authentication.getName()+" not authorized");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("User not authorized");
            return false;
        }

        logger.debug("User "+authentication.getName()+" has been authorized "
                + "to execute "+resourceMethod.getName());

        return true;
    }

    // Extract the privileges from the annotated element
    private List<String> extractPermissions(AnnotatedElement annotatedElement) {
        List<String> permissions = new ArrayList<>();
        if (annotatedElement != null) {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured != null) {
                String[] allowedPermissions = secured.value();
                permissions = Arrays.asList(allowedPermissions);
            }
        }
        return permissions;
    }

    private boolean checkPermissions(List<String> allowedPermissions) {
        // Check if the user contains one of the allowed privileges
        // Throw an Exception if the user has not permission to execute the method
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Allowed privileges : {}", allowedPermissions);
        logger.debug("Roles provided = {}", authentication.getAuthorities());
        //authentication.getAuthorities().forEach(auth -> logger.debug("Authority: {}", auth.getAuthority()));
        for (String allowedPrivilege : allowedPermissions) {
            //logger.debug("Authorities: {} , vs role {} ", authentication.getAuthorities().iterator().next().getClass(), new SimpleGrantedAuthority( allowedPrivilege.getAuthority()).getClass() );
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(allowedPrivilege))) {
                logger.debug("{}'s role {} is authorized", authentication.getName(), allowedPrivilege);
                return true;
            }
        }
        logger.warn("No authorized role found for user {}", authentication.getName());
        return false;
    }

}
