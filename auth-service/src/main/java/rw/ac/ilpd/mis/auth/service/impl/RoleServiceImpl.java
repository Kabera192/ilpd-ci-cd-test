package rw.ac.ilpd.mis.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.entity.jpa.RoleEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.PrivilegeRepository;
import rw.ac.ilpd.mis.auth.repository.jpa.RoleRepository;
import rw.ac.ilpd.mis.auth.service.RoleService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.RoleMapper;
import rw.ac.ilpd.mis.shared.dto.role.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 18/07/2025
 */

@Component
public class RoleServiceImpl implements RoleService {

    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository repository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleMapper mapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {

        try {
            RoleEntity entity = mapper.apiToEntity(role);
            LOG.info("Role created data; context={}", role);
            if(roleRepository.existsByName(role.getName())) throw new RuntimeException("Role already exists");
            Set<PrivilegeEntity> privileges = new HashSet<>(privilegeRepository.findAllById(role.getPrivileges()));
            if (privileges.size() < 1) {
                throw new IllegalArgumentException("Privileges not found: " + privileges);
            }
            entity.setPrivileges(privileges);
            RoleEntity newEntity = repository.save(entity);
            if (newEntity == null) {
                LOG.error("Unable to insert role; context={}", role);
                return null;
            }
            return  mapper.entityToApi(newEntity);
        } catch (Exception e) {
            LOG.error("Insert role failed due to exception: {} {}", role, e.getMessage());
            throw new RuntimeException("Insert role failed due to exception: " + e.getMessage());
        }
    }

    @Override
    public Role updateRole(String roleId, Role role) {
        try {
            RoleEntity entity = repository.findById(UUID.fromString(roleId)).get();
            mapper.apiToEntityToUpdate(role, entity);
            if(!roleRepository.existsByName(role.getName())) throw new RuntimeException("Role does not exist");
            Set<PrivilegeEntity> privileges = new HashSet<>(privilegeRepository.findAllById(role.getPrivileges()));
            if (privileges.size() < 1) {
                throw new IllegalArgumentException("Privileges not found: " + privileges);
            }
            entity.setPrivileges(privileges);
            RoleEntity newEntity = repository.save(entity);

            if (newEntity == null) {
                LOG.error("Unable to update role; context={}", role);
                return null;
            }
            return  mapper.entityToApi(newEntity);

        } catch (Exception e) {
            LOG.error("Update role failed due to exception: {}", role, e);
            throw new RuntimeException("Update role failed due to exception: " + e.getMessage());
        }
    }

    @Override
    public Role getRole(String roleId) {
        try{
            RoleEntity role = repository.findById(UUID.fromString(roleId)).get();
            if (role == null) {
                LOG.debug("No role found; roleId={}", roleId);
                return null;
            }
            LOG.debug("Fetched role; context={}", role);
            return mapper.entityToApi(role);
        }catch (Exception e){
            LOG.error("Get role failed due to exception: {}", roleId, e);
            return null;

        }
    }

    @Override
    public Role getRoleName(String roleName) {
        try{
            RoleEntity role = repository.findByName(roleName).get();
            if (role == null) {
                LOG.debug("No role found; roleName={}", roleName);
                return null;
            }
            LOG.debug("Fetched role; context={}", role);
            return mapper.entityToApi(role);
        }catch (Exception e){
            LOG.error("Get role failed due to exception: {}", roleName, e);
            return null;

        }
    }

    @Override
    public Page<Role> getRoles(Pageable pageable) {

        try{
            Page<RoleEntity> roles = repository.findAll(pageable);
            if (roles.isEmpty()) {
                LOG.debug("No roles found");
                return null;
            }
            LOG.debug("Fetched roles");
            return mapper.entityPageToApiPage(roles);
        }catch (Exception e){
            LOG.error("Get roles failed due to exception: error={}", e);
            return null;

        }
    }

    @Override
    public List<Role> getUnitRoles(String unitId) {

        try{
            List<RoleEntity> roles = repository.findByUnitEntityId(UUID.fromString(unitId));
            if (roles == null) {
                LOG.debug("No roles found for unit; unitId={}", unitId);
                return null;
            }
            LOG.debug("Fetched roles for unit; context={}", unitId);
            return mapper.entityListToApiList(roles);
        }catch (Exception e){
            LOG.error("Get roles for unit failed due to exception: {}", unitId, e);
            return null;

        }
    }

    @Override
    public boolean deleteRole(String roleId) {
        try{
            LOG.debug("deleteRole: tries to delete role with roleId: {}", roleId);
            RoleEntity role = repository.findById(UUID.fromString(roleId)).get();
            repository.delete(role);
        } catch (Exception e) {
            LOG.error("Unable to delete role record : {}", roleId, e);
            return false;
        }
        LOG.debug("Role data deleted : {}", roleId);
        return true;
    }

    @Override
    public List<Role> searchRole(String roleName) {
        try{
            List<RoleEntity> roles = repository.findByNameLikeIgnoreCase(roleName);
            if (roles == null) {
                LOG.debug("No role found like roleName={}", roleName);
                return null;
            }
            LOG.debug("Fetched roles; context={}", roles.size());
            return mapper.entityListToApiList(roles);
        }catch (Exception e){
            LOG.error("Get role failed due to exception: {}", roleName, e);
            return null;
        }
    }

    @Override
    public Role assignPrivileges(List<Privilege> privileges, Role role) {
        return null;
    }

    @Override
    public Role removePrivileges(List<Privilege> privileges, Role role) {
        return null;
    }


}
