package rw.ac.ilpd.paymentservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import rw.ac.ilpd.sharedlibrary.enums.FeeScopeType;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payment_fees")
public class Fee {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    private String name;
    private FeeScopeType scope;
    private  UUID sessionId;
    private BigDecimal amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeeScopeType getScope() {
        return scope;
    }

    public void setScope(FeeScopeType scope) {
        this.scope = scope;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public  Fee(){}
    public Fee(String name, FeeScopeType scope, UUID sessionId, BigDecimal amount) {
        this.name = name;
        this.scope = scope;
        this.sessionId = sessionId;
        this.amount = amount;
    }
    public Fee(UUID id, String name, FeeScopeType scope, UUID sessionId, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.scope = scope;
        this.sessionId = sessionId;
        this.amount = amount;
    }

}
