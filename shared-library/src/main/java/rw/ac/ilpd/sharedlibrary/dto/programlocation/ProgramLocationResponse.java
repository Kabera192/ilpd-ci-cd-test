/**
 * Response payload representing a program-location association in the ILPD system.
 *
 * <p>This DTO returns IDs of the association, linked program and location, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.programlocation;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramLocationResponse {
    private String id;
    private String programId;
    private String locationId;
    private String createdAt;
}
