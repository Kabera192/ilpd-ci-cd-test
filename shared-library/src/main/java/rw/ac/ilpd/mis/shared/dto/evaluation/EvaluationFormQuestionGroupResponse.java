/*
 * File: EvaluationFormQuestionGroupResponse.java
 * 
 * Description: Data Transfer Object representing a group of questions in an evaluation form.
 *              Contains fields for unique identifier, name, description, and creation timestamp.
 *              Includes validation constraints for required fields and maximum length.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com) 
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionGroupResponse {
    private String id;
    private String name;
    private String description;
    private String createdAt;
}