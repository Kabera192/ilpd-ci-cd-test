/*
 * File: TemplateDto.java
 * 
 * Description: Data Transfer Object that represents a document template in the MIS.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.document;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateResponse {
    private String id;
    private String name;
    private DocumentResponse document;
    private Boolean isActive;
}