/**
 * Response payload representing a program type entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, name, delete status, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.programtype;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeResponse {
    private String id;
    private String name;
    private Boolean deleteStatus;
    private String createdAt;
}
