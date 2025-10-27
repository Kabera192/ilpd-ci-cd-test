/**
 * This file defines the FeeResponse DTO used for returning fee data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.fee;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class FeeResponse {
    private String id;
    private String name;
    private String scope;
    private String sessionId;
    private BigDecimal amount;

    public FeeResponse(String id, String name, String scope, String sessionId, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.scope = scope;
        this.sessionId = sessionId;
        this.amount = amount;
    }

}
