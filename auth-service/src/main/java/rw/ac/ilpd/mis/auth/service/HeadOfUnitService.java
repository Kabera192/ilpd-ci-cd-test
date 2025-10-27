package rw.ac.ilpd.mis.auth.service;

import java.util.List;
import rw.ac.ilpd.mis.shared.dto.unit.HeadOfUnit;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */
public interface HeadOfUnitService {

    HeadOfUnit createHeadOfUnit(HeadOfUnit headOfUnit);
    HeadOfUnit updateHeadOfUnit(HeadOfUnit headOfUnit);
    HeadOfUnit getHeadOfUnit(String headOfUnitId);
    List<HeadOfUnit> getHeadOfUnits();
    void deleteHeadOfUnit(String headOfUnitId);
}
