package rw.ac.ilpd.mis.auth.security.jwt;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2024
 */

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.InvalidatedTokenRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.service.impl.UserDetailsImpl;
import rw.ac.ilpd.mis.shared.util.errors.AuthException;
import rw.ac.ilpd.mis.shared.util.errors.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@NoArgsConstructor
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    UserRepository userRepository;

    @Value("${spring.app.jwtIssuer}")
    protected String jwtIssuer;

    @NonFinal
    @Value("${spring.app.jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${spring.app.jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Autowired
    private RSAKey misKey;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove Bearer prefix
        }
        return null;
    }

    public String generateTokenFromUsername(UserDetailsImpl userDetails) {
        var user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_EXISTED));
        return generateToken(userDetails);
    }

    public String getUserNameFromJwtToken(String token){
        try{
            SignedJWT signedJWT = verifyToken(token, false);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        System.out.println("Key: \n" + misKey.toJSONString().getBytes());
        JWSVerifier verifier = new MACVerifier(misKey.toJSONString().getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AuthException(ErrorCode.UNAUTHENTICATED);

        //System.out.println("Token ID: " + signedJWT.getJWTClaimsSet().getJWTID());
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        UserEntity userEntity = userRepository.findByUsername(signedJWT.getJWTClaimsSet().getSubject()).get() ;
        if (invalidatedTokenRepository.existsByIdAndUserId(UUID.fromString(jwtId), userEntity.getId() ) )
            throw new AuthException(ErrorCode.UNAUTHENTICATED);

        //System.out.println("Verified : " + signedJWT.getJWTClaimsSet().getJWTID());
        return signedJWT;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            SignedJWT signedJWT = verifyToken(authToken, false);
            System.out.println("Validate: "+ signedJWT.getJWTClaimsSet());

            return true ? signedJWT!=null: false;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException("Malformed JWT token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new ExpiredJwtException(null, null, "Expired jwt token");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("JWT claims string is empty");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(UserDetailsImpl userDetails) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        var user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_EXISTED));
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer(jwtIssuer)
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            System.out.println(misKey.toJSONString());
            jwsObject.sign(new MACSigner(misKey.toJSONString().getBytes()));
            return  jwsObject.serialize();

        } catch (JOSEException e) {
            logger.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }


    private String buildScope(UserEntity userEntity) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Set<RoleEntity> roleEntities = userEntity.getRoles();
        Set<PrivilegeEntity> privilegeEntities = userEntity.getPrivileges();
        if ( !roleEntities.isEmpty()) {
            roleEntities.forEach(role -> {
                stringJoiner.add(role.getName());
                if (!CollectionUtils.isEmpty(role.getPrivileges()))
                    role.getPrivileges().forEach(privilege -> stringJoiner.add(privilege.getName()));
            });
        }
        if ( !privilegeEntities.isEmpty()) {
            privilegeEntities.forEach(privilege -> {
                stringJoiner.add(privilege.getName());
            });
        }

        return stringJoiner.toString();
    }

}