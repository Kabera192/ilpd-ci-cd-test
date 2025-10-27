package rw.ac.ilpd.mis.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.shared.dto.unit.Unit;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */
public interface UnitService {
    Unit createUnit(Unit unit);
    Unit updateUnit(String unitId, Unit unit);
    Unit getUnit(String unitId);
    Unit getUnitByName(String name);
    Page<Unit> getUnits(Pageable pageable);
    boolean deleteUnit(String unitId);
}
