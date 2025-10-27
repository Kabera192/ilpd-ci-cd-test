/**
 * This file defines the CourseMaterialResponse DTO used for returning course material data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.coursematerial;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseMaterialResponse {
    private String id;
    private String title;
    private String description;
    private String documentId;
    private String createdAt;
}
