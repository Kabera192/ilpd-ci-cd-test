package rw.ac.ilpd.mis.auth.service;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 14/07/2025
 */
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.util.Map;

public interface TotpService {
    GoogleAuthenticatorKey generateSecret();
    String getGoogleQrCodeUrl(GoogleAuthenticatorKey secret, String username);
    public String generateQrCode(String userId);
    public Map<String, String> generateSecretForUser(String userId);
    boolean verifyCode(String secret, int code, String userId, String loginCode);
    void generateOtp(String userId, String loginCode) throws Exception;
    boolean validateOtp(String username, String loginCode, String otp);

    String otpKey(String username, String loginCode);

    String createUniqueId();
}
