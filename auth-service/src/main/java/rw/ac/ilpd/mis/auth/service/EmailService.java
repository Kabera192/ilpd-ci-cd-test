package rw.ac.ilpd.mis.auth.service;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 19/07/2025
 */
public interface EmailService {
    void sendPasswordResetEmail(String to, String resetUrl);
    void sendOtpEmail(String to, String otp);
}
