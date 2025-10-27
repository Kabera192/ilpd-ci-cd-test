package rw.ac.ilpd.mis.auth.service;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;

import java.util.List;

public interface RoleService {

    Role getRole(String roleId);
    Role getRoleName(String roleName);
    Page<Role> getRoles(Pageable pageable);
    List<Role> getUnitRoles(String unitId);
    Role createRole(Role role);
    Role updateRole(String roleId, Role role);
    boolean deleteRole(String roleId);
    List<Role> searchRole(String keyword);
    Role assignPrivileges(List<Privilege> privileges, Role role);
    Role removePrivileges(List<Privilege> privileges, Role role);
}
