package rw.ac.ilpd.mis.shared.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Privilege {
    private UUID id;
    private String name;
    private String description;
    private String path;
    private String service;

    public Privilege(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
