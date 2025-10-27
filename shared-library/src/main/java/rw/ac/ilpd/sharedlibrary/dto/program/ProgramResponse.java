/**
 * Response payload representing a program entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, code, name, description, acronym, program type, deletion status, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.program;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramResponse {
    private String id;
    private String code;
    private String name;
    private String description;
    private String acronym;
    private String programTypeId;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
