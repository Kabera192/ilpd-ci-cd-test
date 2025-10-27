package rw.ac.ilpd.mis.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.UnitEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.UnitRepository;
import rw.ac.ilpd.mis.auth.service.UnitService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.UnitMapper;
import rw.ac.ilpd.mis.shared.dto.unit.Unit;

import java.util.List;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 21/08/2025
 */

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UnitMapper unitMapper;

    Logger LOG = LoggerFactory.getLogger(UnitServiceImpl.class);

    @Override
    public Unit createUnit(Unit unit) {
        try {
            UnitEntity entity = unitMapper.apiToEntity(unit);
            UnitEntity newEntity = unitRepository.save(entity);

            if (newEntity == null) {
                LOG.error("Unable to insert unit; context={}", unit);
                return null;
            }
            return unitMapper.entityToApi(newEntity);
        } catch (Exception e) {
            LOG.error("Insert unit failed due to exception: {}", unit, e);
            throw e;
        }
    }

    @Override
    public Unit updateUnit(String unitId, Unit unit) {
        try {
            LOG.error("Update unit; context={}", unit);
            UnitEntity entity = unitRepository.findById(UUID.fromString(unitId)).get();
            unitMapper.apiToEntityToUpdate(unit, entity);
            UnitEntity newEntity = unitRepository.save(entity);

            if (newEntity == null) {
                LOG.error("Unable to update unit; context={}", unitId);
                return null;
            }

            return unitMapper.entityToApi(newEntity);
        } catch (Exception e) {
            LOG.error("Update unit failed due to exception: {}", unit, e);
            throw e;
        }
    }

    @Override
    public Unit getUnit(String unitId) {
        try{
            UnitEntity unit = unitRepository.findById(UUID.fromString(unitId)).get();
            if (unit == null) {
                LOG.debug("No unit found; unitId={}", unitId);
                return null;
            }
            LOG.debug("Fetched unit; context={}", unit);
            return unitMapper.entityToApi(unit);
        }catch (Exception e){
            LOG.error("Get unit failed due to exception: {}", unitId, e);
            return null;

        }
    }

    @Override
    public Unit getUnitByName(String name) {
        try{
            UnitEntity unit = unitRepository.findByName(name).get();
            if (unit == null) {
                LOG.debug("No unit found; unitName={}", name);
                return null;
            }
            LOG.debug("Fetched unit; context={}", unit);
            return unitMapper.entityToApi(unit);
        }catch (Exception e){
            LOG.error("Get unit failed due to exception: {}", name, e);
            return null;

        }
    }

    @Override
    public Page<Unit> getUnits(Pageable pageable) {
        try{
            Page<UnitEntity> units = unitRepository.findAll(pageable);
            if (units.isEmpty()) {
                LOG.debug("No Units found");
                return null;
            }
            LOG.debug("Fetched units: {}", units.getTotalElements());
            return unitMapper.entityPageToApiPage(units);
        }catch (Exception e){
            LOG.error("Get units failed due to exception: error={}", e);
            return null;
        }
    }

    @Override
    public boolean deleteUnit(String unitId) {
        try{
            LOG.debug("deleteUnit: tries to delete unit with unitId: {}", unitId);
            UnitEntity unit = unitRepository.findById(UUID.fromString(unitId)).get();
            unitRepository.delete(unit);
        } catch (Exception e) {
            LOG.error("Unable to delete unit record : {}", unitId, e);
            return false;
        }
        LOG.debug("Unit data deleted : {}", unitId);
        return true;
    }
}
