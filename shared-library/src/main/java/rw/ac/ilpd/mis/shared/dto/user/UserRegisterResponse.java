/**
 * Response payload after registering a new user in the ILPD system.
 *
 * <p>This DTO returns the user's ID, full names, and email address.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponse {
    private String id;
    private String names;
    private String email;
}
