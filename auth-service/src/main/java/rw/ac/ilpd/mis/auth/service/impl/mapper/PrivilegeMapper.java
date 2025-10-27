package rw.ac.ilpd.mis.auth.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;

import java.util.List;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 25/07/2025
 */

@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    Privilege entityToApi(PrivilegeEntity privilegeEntity);

    //@Mapping(target = "id", ignore = true)
    PrivilegeEntity apiToEntity(Privilege privilege);

    List<Privilege> entityListToApiList(List<PrivilegeEntity> entity);

    List<PrivilegeEntity> apiListToEntityList(List<Privilege> api);

    void apiToEntityToUpdate(Privilege privilege, @MappingTarget PrivilegeEntity privilegeEntity);

    default UUID toId(PrivilegeEntity e) {
        return e == null ? null : e.getId();
    }

    // ID -> Entity (stub with only id set)
    default PrivilegeEntity fromId(UUID id) {
        if (id == null) return null;
        PrivilegeEntity e = new PrivilegeEntity();
        e.setId(id);
        return e;
    }

    default Page<Privilege> entityPageToApiPage(Page<PrivilegeEntity> page) {
        List<Privilege> dtoList = entityListToApiList(page.getContent());
        return new org.springframework.data.domain.PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}
