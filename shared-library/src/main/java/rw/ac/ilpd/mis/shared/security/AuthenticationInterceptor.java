package rw.ac.ilpd.mis.shared.security;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rw.ac.ilpd.mis.shared.client.*;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;
import rw.ac.ilpd.mis.shared.config.ServiceID;
import rw.ac.ilpd.mis.shared.util.helpers.JsonUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 12/07/2025
 */

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    private int httpClientPoolSize = 1;

    @Autowired
    private ApiClientFactory apiClientFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            logger.info("No Authorization header present in the request; requestContext={}", request.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("User does not have an authentication token");
            return false;
        }

        logger.debug("Auth header : "+authHeader);

        UserAuth userAuth = getUserCred(authHeader);
        if (userAuth == null) {
            logger.info("Null access token");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("User does not have a valid authentication token");
            return false;
        }

        MisAuthentication appAuthentication = new MisAuthentication(userAuth,
                request.isSecure(), authHeader);
        appAuthentication.setAuthenticated(true);
        MisSecurityContext appSecurityContext = new MisSecurityContext(appAuthentication);
        request.setAttribute("AppSecurityContext",appSecurityContext);
        SecurityContextHolder.setContext(appSecurityContext);
        logger.debug("User authenticated; context={}", userAuth);

        return true;
    }

    private UserAuth getUserCred(String authHeader) {
        if(authHeader==null || !authHeader.startsWith("Bearer")) {
            //logger.error("Authorization not provided; authHeader={}", authHeader);
            return null;
        }

        try {

            // Behind a load balancer and gateway
            ApiClient apiClient = apiClientFactory.createApiClient(ServiceID.AuthSvc, authHeader);
            HttpClientResponse response = apiClient.getAuthHeader();

            //logger.info("Auth response; context={}", response);
            //UserAuth userAuth =  JsonUtility.parse(response.getBody(), UserAuth.class) ;
            JsonObject fullJson = JsonParser.parseString(response.getBody()).getAsJsonObject();
            //logger.info("Auth response; context={}", fullJson);
            JsonObject userJson = fullJson.getAsJsonObject ("result");
            //logger.info("User AUTH JSON; context={}", fullJson);
            //logger.info("User JSON; context={}", userJson);
            UserAuth userAuth =  toUserAuth(JsonUtility.parse(userJson.toString(), User.class));
            //logger.debug("User authenticated; context={}", userAuth);
            return userAuth;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static UserAuth toUserAuth(User  user) {
        if (user == null) { return null; }
        UserAuth userAuth = new UserAuth();
        userAuth.setId(user.getId().toString());
        userAuth.setUsername(user.getUsername());
        userAuth.setEmail(user.getEmail());
        userAuth.setPassword(user.getPassword());
        userAuth.setFirstName(user.getNames());
        userAuth.setDateOfBirth(user.getDateOfBirth());

        userAuth.setAccountNonLocked(user.isAccountNonLocked());
        userAuth.setAccountNonExpired(user.isAccountNonExpired());
        userAuth.setCredentialsNonExpired(user.isCredentialsNonExpired());
        userAuth.setEnabled(user.isActive());

        userAuth.setCredentialsExpiryDate(user.getCredentialsExpiryDate());
        userAuth.setAccountExpiryDate(user.getAccountExpiryDate());

        userAuth.setTwoFactorSecret(user.getTwoFactorSecret());
        userAuth.setTwoFactorEnabled(user.isTwoFactorEnabled());
        userAuth.setSignUpMethod(user.getSignUpMethod());
        userAuth.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        userAuth.setPermissions(user.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        userAuth.setAuthorities(
                Stream.concat(userAuth.getRoles().stream(), userAuth.getPermissions().stream())
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toCollection(ArrayList::new))
        );

        userAuth.setCreatedDate(user.getCreatedAt());
        userAuth.setUpdatedDate(user.getUpdatedAt());
        userAuth.setTelephone(user.getPhoneNumber());
        userAuth.setIdentityNumber(user.getNationalIdentity());
        return userAuth;
    }



}
