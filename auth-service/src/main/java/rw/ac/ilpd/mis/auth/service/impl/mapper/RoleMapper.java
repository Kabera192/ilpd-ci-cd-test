package rw.ac.ilpd.mis.auth.service.impl.mapper;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.shared.dto.role.Role;

@Mapper(componentModel = "spring", uses = { PrivilegeMapper.class })
public interface RoleMapper {

    @Mapping(source = "unitEntity.id", target = "unitId")
    @Mapping(target = "privileges", source = "privileges")
    Role entityToApi(RoleEntity roleEntity);

    @InheritInverseConfiguration                                   // DTO -> Entity
    @Mappings({
            //@Mapping(target = "id", ignore = true),
            @Mapping(source = "unitId", target = "unitEntity.id"),
            @Mapping(target = "privileges", source = "privileges")
    })

    RoleEntity apiToEntity(Role role);

    List<Role> entityListToApiList(List<RoleEntity> entity);

    List<RoleEntity> apiListToEntityList(List<Role> api);

    void apiToEntityToUpdate(Role role, @MappingTarget RoleEntity roleEntity);

    default Set<UUID> mapPrivilegesToIds(Set<PrivilegeEntity> privileges) {
        return privileges.stream().map(PrivilegeEntity::getId).collect(Collectors.toSet());
    }

    default Set<PrivilegeEntity> mapIdsToPrivileges(Set<UUID> ids) {
        return ids.stream().map(id -> {
            PrivilegeEntity privilege = new PrivilegeEntity();
            privilege.setId(id);
            return privilege;
        }).collect(Collectors.toSet());
    }

    default Set<String> mapPrivilegeNamesToNames(Set<PrivilegeEntity> privileges) {
        return privileges.stream().map(PrivilegeEntity::getName).collect(Collectors.toSet());
    }

    default Set<PrivilegeEntity> mapNamesToPrivilegeNames(Set<String> names) {
        return names.stream().map(name -> {
            PrivilegeEntity privilege = new PrivilegeEntity();
            privilege.setName(name);
            return privilege;
        }).collect(Collectors.toSet());
    }

    default Page<Role> entityPageToApiPage(Page<RoleEntity> page) {
        List<Role> dtoList = entityListToApiList(page.getContent());
        return new org.springframework.data.domain.PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}