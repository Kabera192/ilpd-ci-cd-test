/**
 * This file defines the ComponentResponse DTO used for returning component data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.component;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentResponse {
    private String id;
    private String name;
    private String acronym;
    private String curriculumModuleId;
    private String createdAt;
    private Integer credits;
}
