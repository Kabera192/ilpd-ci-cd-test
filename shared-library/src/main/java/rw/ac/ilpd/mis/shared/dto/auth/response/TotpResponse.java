package rw.ac.ilpd.mis.shared.dto.auth.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 30/07/2025
 */

@Data
@NoArgsConstructor
public class TotpResponse {
    private String id;
    private String username;
    private String email;
    private boolean isTwoFactorEnabled;
    private String signUpMethod;
    private String loginCode;

    public TotpResponse(String id, String username, String email, boolean isTwoFactorEnabled, String signUpMethod, String loginCode) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.signUpMethod = signUpMethod ;
        this.loginCode = loginCode;
    }
}