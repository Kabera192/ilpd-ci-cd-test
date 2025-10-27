package rw.ac.ilpd.mis.auth.service.impl;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.UserRepository;
import rw.ac.ilpd.mis.auth.service.TotpService;
import rw.ac.ilpd.mis.shared.util.helpers.UniqueIdGenerator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 19/07/2025
 */

@Service
public class TotpServiceImpl implements TotpService {

    @Value("${spring.app.jwtIssuer}")
    protected String jwtIssuer;

    @Value("${spring.app.jwtSecret}")
    private static String ref = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String TOTP_URI_FORMAT =
            "https://api.qrserver.com/v1/create-qr-code/?data=%s&size=200x200&ecc=M&margin=0";

    private static final Duration OTP_TTL = Duration.ofMinutes(5);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailServiceImpl emailServiceImpl;
    private final GoogleAuthenticator gAuth;
    public TotpServiceImpl(GoogleAuthenticator gAuth) {
        this.gAuth = gAuth;
    }

    public TotpServiceImpl() {
        this.gAuth = new GoogleAuthenticator();
    }

    @Override
    public GoogleAuthenticatorKey generateSecret(){
        return gAuth.createCredentials();
    }

    @Override
    public String getGoogleQrCodeUrl(GoogleAuthenticatorKey secret, String username){
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(jwtIssuer, username, secret);
    }

    @Override
    public String generateQrCode(String userId) {
        UserEntity userTotp = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (userTotp == null) return null;

        String secret = userTotp.getTwoFactorSecret();
        String encodedIssuer = URLEncoder.encode(jwtIssuer, StandardCharsets.UTF_8);
        String encodedUser = URLEncoder.encode(userTotp.getEmail(), StandardCharsets.UTF_8);

        String otpAuthUrl = String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                encodedIssuer, encodedUser, secret, encodedIssuer
        );

        return String.format(
                TOTP_URI_FORMAT,
                internalURLEncode(otpAuthUrl));
    }

    @Override
    public Map<String, String> generateSecretForUser(String  userId) {
        byte[] buffer = new byte[20];
        new SecureRandom().nextBytes(buffer);
        String secret = Base64.getEncoder().encodeToString(buffer);

        UserEntity userTotp = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                -> new RuntimeException("User not found"));
        userTotp.setTwoFactorSecret(secret);
        userRepository.save(userTotp);

        Map<String, String> response = new HashMap<>();
        response.put("user", userTotp.getEmail());
        response.put("secret", secret);
        return response;
    }


    @Override
    public boolean verifyCode(String secret, int code, String userId, String loginCode){
        UserEntity userTotp = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (userTotp == null) return false;
        String signupMethod = userTotp.getSignUpMethod();
        return switch (signupMethod){
            case "email", "telephone" -> validateOtp(userTotp.getUsername(), String.valueOf(code), loginCode );
            case "authapp" -> gAuth.authorize(secret, code);
            default -> false;
        };
    }
    @Override
    public void generateOtp(String userId, String loginCode) throws Exception {
        UserEntity userTotp = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (userTotp == null) throw new Exception("User not found");
        String otp = String.valueOf(100000 + new SecureRandom().nextInt(900000));
        redisTemplate.opsForValue().set(otpKey(userTotp.getUsername(),loginCode), otp, OTP_TTL);
        // Send real email/SMS sending in case AUTHAPP is not enabled
        if(!userTotp.getSignUpMethod().equalsIgnoreCase("authapp")) {
            System.out.println("OTP for " + userTotp.getUsername() + ": " + otp);
            emailServiceImpl.sendOtpEmail(userTotp.getEmail(), otp);
        }
    }

    @Override
        public boolean validateOtp(String username, String otp, String loginCode) {
        String storedOtp = redisTemplate.opsForValue().get(otpKey(username,loginCode));
        if (storedOtp != null && storedOtp.equals(otp)) {
            redisTemplate.delete(otpKey(username,loginCode));
            // load the token, and privileges TODO
            return true;
        }
        return false;
    }

    public String otpKey(String username, String loginCode) {
        return "otp:" + loginCode + username;
    }

    public String createUniqueId() {
        long uniqueId = UniqueIdGenerator.getInstance().nextId();
        return encodeToBase36(uniqueId);
    }
    public static String encodeToBase36(long num) {
        if (num < 0) {
            num = -num;
        }
        String result = "";
        while (num > 0) {
            result = ref.charAt((int) (num%36)) + result;
            num /= 36;
        }
        return result;
    }

    private static String internalURLEncode(String s)
    {
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("UTF-8 encoding is not supported by URLEncoder.", e);
        }
    }
}
