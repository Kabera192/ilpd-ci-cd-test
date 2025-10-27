/*
 * File: ItemResponse.java
 * 
 * Description: Data Transfer Object representing an inventory item.
 *              Contains fields such as id, name, acronym, description, category, groupId,
 *              unitMeasure, depreciationRate, isInStock, and createdAt.
 *              Validation constraints ensure required fields and correct formats.
 *              Lombok annotations are used for boilerplate code generation.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.inventory;

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    private String id;
    private String name;
    private String acronym;
    private String description;
    private String category;
    private String groupId;
    private String unitMeasure;
    private BigDecimal depreciationRate;
    private Integer remainingQuantity;
    private String createdAt;
}