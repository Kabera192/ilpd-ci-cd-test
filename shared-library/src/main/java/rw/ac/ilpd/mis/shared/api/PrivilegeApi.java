package rw.ac.ilpd.mis.shared.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 23/07/2025
 */
public interface PrivilegeApi {
    ResponseEntity<?> getAllPrivileges(int page, int pageSize, String sortBy);
    /**
     * Sample usage: "curl $HOST:$PORT/$AUTH_PATH/privileges/1".
     *
     * @param privilegeId Id of the privilege
     * @return the privilege, if found, else null
     */
    ResponseEntity<?> getPrivilege(@PathVariable String privilegeId);
    ResponseEntity<?> getPrivilegeByName(@RequestParam String privilegeName);
    ResponseEntity<?> searchPrivilege(@RequestParam String keyword);
    ResponseEntity<?> createPrivilege(Privilege body);
    ResponseEntity<?> updatePrivilege(@PathVariable String privilegeId, Privilege body);
    ResponseEntity<?> deletePrivilege(@PathVariable String privilegeId);
}
