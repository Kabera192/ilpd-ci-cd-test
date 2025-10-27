package rw.ac.ilpd.mis.auth.entity.jpa;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */

@Entity
@Table(name = "auth_privileges")
public class PrivilegeEntity {
    @Id
    @GeneratedValue
    @Column(name="privilege_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String path;
    private String service;

    public PrivilegeEntity(String name, String description, String path, String service) {
        this.name = name;
        this.description = description;
        this.path = path;
        this.service = service;
    }

    public PrivilegeEntity() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "PrivilegeEntity{" +
                "privilegeId=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", service='" + service + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
