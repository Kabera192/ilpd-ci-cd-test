package rw.ac.ilpd.mis.auth.entity.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier
 * @project mis
 * @date 10/07/2025
 */

@Entity
@Table(name = "auth_units")
public class UnitEntity {

    @Id
    @GeneratedValue
    @Column(name = "unit_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String acronym;

    private String description;

    @OneToMany(mappedBy = "unitEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonBackReference
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "unitEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonBackReference
    private Set<HeadOfUnitEntity> heads = new HashSet<>();

    // === Getters and Setters ===

    public UnitEntity() {}

    public UnitEntity(String name, String acronym, String description) {
        this.name = name;
        this.acronym = acronym;
        this.description = description;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    // Optional: toString override (exclude roles to avoid recursion)

    @Override
    public String toString() {
        return "UnitEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
