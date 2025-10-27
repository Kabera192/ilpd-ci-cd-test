package rw.ac.ilpd.mis.auth.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import rw.ac.ilpd.mis.auth.entity.jpa.UnitEntity;
import rw.ac.ilpd.mis.shared.dto.unit.Unit;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 21/08/2025
 */

@Mapper(componentModel = "spring")
public interface UnitMapper {

    Unit entityToApi(UnitEntity unitEntity);

    UnitEntity apiToEntity(Unit unit);

    List<Unit> entityListToApiList(List<UnitEntity> unitEntityList);
    List<UnitEntity> apiListToEntityList(List<Unit> unitList);

    void apiToEntityToUpdate(Unit unit, @MappingTarget UnitEntity unitEntity);

    default Page<Unit> entityPageToApiPage(Page<UnitEntity> page) {
        List<Unit> dtoList = entityListToApiList(page.getContent());
        return new org.springframework.data.domain.PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
    }
}
