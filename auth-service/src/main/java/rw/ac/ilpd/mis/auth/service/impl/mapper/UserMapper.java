package rw.ac.ilpd.mis.auth.service.impl.mapper;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 26/07/2025
 */

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UserEntity;
import rw.ac.ilpd.mis.shared.dto.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper {

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "twoFactorSecret", ignore = true),
            @Mapping(target = "roles", source = "roles")
    })
    User entityToApi(UserEntity userEntity);

    @InheritInverseConfiguration
    @Mappings({
            //@Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "twoFactorSecret", ignore = true)
    })
    UserEntity apiToEntity(User user);

    List<User> entityListToApiList(List<UserEntity> entities);

    List<UserEntity> apiListToEntityList(List<User> users);

    void apiToEntityToUpdate(User user, @MappingTarget UserEntity userEntity);

    /*
     * In case of many to many relationships we need to map with what we think unique identifier
     * and which is meaningful, try to avoid using UUID ids
     */
    default Set<String> mapRolesToNames(Set<RoleEntity> roles) {
        if (roles == null) return null;
        return roles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
    }

    default Set<RoleEntity> mapIdsToRoles(Set<String> names) {
        return names.stream().map(name -> {
            RoleEntity role = new RoleEntity();
            role.setName(name);
            return role;
        }).collect(Collectors.toSet());
    }

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

    default Page<User> entityPageToApiPage(Page<UserEntity> page) {
        List<User> dtoList = entityListToApiList(page.getContent());
        return new org.springframework.data.domain.PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}
