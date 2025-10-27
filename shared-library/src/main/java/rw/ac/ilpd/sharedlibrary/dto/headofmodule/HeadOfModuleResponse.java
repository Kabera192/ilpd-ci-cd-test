/**
 * This file defines the HeadsOfModuleResponse DTO used for returning heads of module assignments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.headofmodule;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeadOfModuleResponse {
    private String id;
    private String lecturerId;
    private String moduleId;
    private String from;
    private String to;
    private String createdAt;
}
