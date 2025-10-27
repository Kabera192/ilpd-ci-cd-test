package rw.ac.ilpd.mis.auth.service;

import java.util.List;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */
public interface PrivilegeService {
    Privilege getPrivilege(String privilegeId);
    Privilege getPrivilegeName(String privilegeName);
    Page<Privilege> getPrivileges(Pageable pageable);
    boolean createPrivilege(Privilege privilege);
    boolean updatePrivilege(String privilegeId, Privilege privilege);
    boolean deletePrivilege(String privilegeId);
    List<Privilege> searchPrivilege(String keyword);
}
