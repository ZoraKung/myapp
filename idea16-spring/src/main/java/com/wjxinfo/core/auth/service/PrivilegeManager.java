package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.PrivilegeDao;
import com.wjxinfo.core.auth.model.Privilege;
import com.wjxinfo.core.base.service.BaseManager;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("privilegeManager")
public class PrivilegeManager extends BaseManager<Privilege> {
    @Qualifier("privilegeDao")
    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public void remove(Privilege privilege) {
        logger.debug("privilege delete manager id: " + privilege.getId());
        if (privilege != null) {
            logger.debug("privilege name: " + privilege.getName());
            privilege.getRoles().clear();
            privilegeDao.delete(privilege);
        }
    }

    public List<Privilege> getPrivilegeList(String userId) {
        return privilegeDao.getPrivilegeList(userId);
    }

    @Override
    public void remove(String ids) {
        logger.debug("privilege delete manager ids: " + ids);
        String[] arrIds = ids.split(",");
        for (String id : arrIds) {
            this.remove(getEntityById(id));
        }
    }

    public Privilege getPrivilegeByName(String privilegeName) {
        List<Privilege> privileges = privilegeDao.findPrivilegeByName(privilegeName);
        if (CollectionHelper.isNotEmpty(privileges)) {
            return privileges.get(0);
        } else {
            return null;
        }
    }


}
