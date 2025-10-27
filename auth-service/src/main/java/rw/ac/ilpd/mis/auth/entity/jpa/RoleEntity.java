package rw.ac.ilpd.mis.auth.entity.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "auth_roles")
public class RoleEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    @JsonBackReference
    private UnitEntity unitEntity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "auth_role_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<PrivilegeEntity> privileges;

    public RoleEntity() {}

    public RoleEntity(String name, UnitEntity unitEntity) {
        this.name = name;
        this.unitEntity = unitEntity;
    }

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

    public UnitEntity getUnitEntity() {
        return unitEntity;
    }

    public void setUnitEntity(UnitEntity unitEntity) {
        this.unitEntity = unitEntity;
    }

    public Set<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }
}
