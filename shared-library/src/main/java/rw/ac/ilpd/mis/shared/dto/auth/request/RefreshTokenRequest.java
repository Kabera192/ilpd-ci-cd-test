package rw.ac.ilpd.mis.shared.dto.auth.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 11/07/2025
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequest {
    String token;
}
