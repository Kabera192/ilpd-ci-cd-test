/**
 * This file defines the InstitutionShortCourseSponsorResponse DTO used to return short course sponsor data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.institutionshortcoursesponsor;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionShortCourseSponsorResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String type;
}
