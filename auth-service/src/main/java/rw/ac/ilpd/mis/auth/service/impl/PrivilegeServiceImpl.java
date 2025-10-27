package rw.ac.ilpd.mis.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.auth.entity.jpa.PrivilegeEntity;
import rw.ac.ilpd.mis.auth.repository.jpa.PrivilegeRepository;
import rw.ac.ilpd.mis.auth.service.PrivilegeService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.PrivilegeMapper;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2025
 */

@Component
public class PrivilegeServiceImpl implements PrivilegeService {

    private static final Logger LOG = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    @Autowired
    PrivilegeRepository repository;

    @Autowired
    PrivilegeMapper mapper;

    @Override
    public boolean createPrivilege(Privilege privilege) {

        try {
            PrivilegeEntity entity = mapper.apiToEntity(privilege);
            PrivilegeEntity newEntity = repository.save(entity);
            mapper.entityToApi(newEntity);
            if (newEntity == null) {
                LOG.error("Unable to insert privilege; context={}", privilege);
                return false;
            }
        } catch (Exception e) {
            LOG.error("Insert privilege failed due to exception: {}", privilege, e);
            return false;
        }
        LOG.debug("createPrivilege: created a privilege entity: {}", privilege.getId());
        return true;
    }

    @Override
    public boolean updatePrivilege(String privilegeId, Privilege privilege) {
        try {
            PrivilegeEntity entity = repository.findById(UUID.fromString(privilegeId)).get();
            mapper.apiToEntityToUpdate(privilege, entity);
            PrivilegeEntity newEntity = repository.save(entity);

            if (newEntity == null) {
                LOG.error("Unable to update privilege; context={}", privilege);
                return false;
            }

        } catch (Exception e) {
            LOG.error("Update privilege failed due to exception: {}", privilege, e);
            return false;
        }
        LOG.debug("updatePrivilege: updated a privilege entity: {}", privilege.getId());
        return true;
    }

    @Override
    public Privilege getPrivilege(String privilegeId) {
        try{
            PrivilegeEntity privilege = repository.findById(UUID.fromString(privilegeId)).get();
            if (privilege == null) {
                LOG.debug("No privilege found; privilegeId={}", privilegeId);
                return null;
            }
            LOG.debug("Fetched privilege; context={}", privilege);
            return mapper.entityToApi(privilege);
        }catch (Exception e){
            LOG.error("Get privilege failed due to exception: {}", privilegeId, e);
            return null;

        }
    }

    @Override
    public Privilege getPrivilegeName(String privilegeName) {
        try{
            PrivilegeEntity privilege = repository.findByName(privilegeName).get();
            if (privilege == null) {
                LOG.debug("No privilege found; privilegeName={}", privilegeName);
                return null;
            }
            LOG.debug("Fetched privilege; context={}", privilege);
            return mapper.entityToApi(privilege);
        }catch (Exception e){
            LOG.error("Get privilege failed due to exception: {}", privilegeName, e);
            return null;

        }
    }

    @Override
    public Page<Privilege> getPrivileges(Pageable pageable ) {

        try{
            Page<PrivilegeEntity> privileges = repository.findAll(pageable);
            if (privileges.isEmpty()) {
                LOG.debug("No privileges found");
                return null;
            }
            LOG.debug("Fetched privileges");
            return mapper.entityPageToApiPage(privileges);
        }catch (Exception e){
            LOG.error("Get privileges failed due to exception: error={}", e);
            return null;

        }
    }

    @Override
    public boolean deletePrivilege(String privilegeId) {
        try{
            LOG.debug("deletePrivilege: tries to delete privilege with privilegeId: {}", privilegeId);
            PrivilegeEntity privilege = repository.findById(UUID.fromString(privilegeId)).get();
            repository.delete(privilege);
        } catch (Exception e) {
            LOG.error("Unable to delete privilege record : {}", privilegeId, e);
            return false;
        }
        LOG.debug("Privilege data deleted : {}", privilegeId);
        return true;
    }

    @Override
    public List<Privilege> searchPrivilege(String privilegeName) {
        try{
            List<PrivilegeEntity> privileges = repository.findByNameLikeIgnoreCase(privilegeName);
            if (privileges == null) {
                LOG.debug("No privilege found like privilegeName={}", privilegeName);
                return null;
            }
            LOG.debug("Fetched privileges; context={}", privileges.size());
            return mapper.entityListToApiList(privileges);
        }catch (Exception e){
            LOG.error("Get privilege failed due to exception: {}", privilegeName, e);
            return null;
        }
    }
}

