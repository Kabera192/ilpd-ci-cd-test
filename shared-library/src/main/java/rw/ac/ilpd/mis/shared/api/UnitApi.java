package rw.ac.ilpd.mis.shared.api;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import rw.ac.ilpd.mis.shared.dto.unit.Unit;

public interface UnitApi {

    ResponseEntity<?> getAllUnits(int page, int size, String sort);
    /**
     * Sample usage: "curl $HOST:$PORT/$AUTH_PATH/units/1".
     *
     * @param unitId Id of the unit
     * @return the unit, if found, else null
     */
    ResponseEntity<?> getUnit(@PathVariable String unitId);
    ResponseEntity<?> getUnitByName(@RequestParam String unitName);
    ResponseEntity<?> createUnit(Unit body);
    ResponseEntity<?> updateUnit(@PathVariable String unitId, Unit body);
    ResponseEntity<?> deleteUnit(@PathVariable String unitId);
}