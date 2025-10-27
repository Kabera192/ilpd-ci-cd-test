package rw.ac.ilpd.mis.shared.api;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 16/07/2025
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import rw.ac.ilpd.mis.shared.dto.role.Role;

public interface RoleApi {

    ResponseEntity<?> getAllRoles(int page, int size, String sort);
    ResponseEntity<?> getUnitRoles(String unitId);
    /**
     * Sample usage: "curl $HOST:$PORT/$AUTH_PATH/roles/1".
     *
     * @param roleId Id of the role
     * @return the role, if found, else null
     */
    ResponseEntity<?> getRole(@PathVariable String roleId);
    ResponseEntity<?> getRoleByName(@RequestParam String roleName);
    ResponseEntity<?> searchRole(@RequestParam String keyword);
    ResponseEntity<?> createRole(Role body);
    ResponseEntity<?> updateRole(@PathVariable String roleId, Role body);
    ResponseEntity<?> deleteRole(@PathVariable String roleId);
}