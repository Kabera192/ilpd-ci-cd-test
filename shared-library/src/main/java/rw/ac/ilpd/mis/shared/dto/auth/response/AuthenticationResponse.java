/**
 * This file defines the AuthenticationResponse DTO used for returning authentication tokens.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private String username;
    private List<String> privileges;

    public AuthenticationResponse(String username, List<String> privileges, String token) {
        this.username = username;
        this.privileges = privileges;
        this.token = token;
    }
}
