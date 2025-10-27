package rw.ac.ilpd.mis.shared.dto.auth.request;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 11/07/2025
 */

public class LogoutRequest {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
