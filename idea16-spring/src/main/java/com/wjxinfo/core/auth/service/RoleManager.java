package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.RoleDao;
import com.wjxinfo.core.auth.dao.UserDao;
import com.wjxinfo.core.auth.model.Role;
import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.base.service.BaseManager;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleManager")
public class RoleManager extends BaseManager<Role> {
    private static final Logger logger = LoggerFactory.getLogger(RoleManager.class);

    @Qualifier("roleDao")
    @Autowired
    private RoleDao roleDao;

    @Qualifier("userDao")
    @Autowired
    private UserDao userDao;

    @Override
    public void remove(Role role) {
        logger.debug("role delete manager id: " + role.getId());
        if (role != null) {
            List<User> users = userDao.getUsersByRoleId(role.getId());
            for (User user : users) {
                user.getRoles().remove(role);
            }
            role.getUsers().clear();
            role.getPrivileges().clear();
            roleDao.delete(role);
        }
    }

    @Override
    public void remove(String ids) {
        logger.debug("role delete manager ids: " + ids);
        String[] arrIds = ids.split(",");
        if (arrIds.length > 0) {
            for (String id : arrIds) {
                this.remove(getEntityById(id));
            }
        }
    }

    public Role getRoleByName(String name) {
        List<Role> roles = roleDao.findByName(name);
        if (CollectionHelper.isNotEmpty(roles)) {
            return roles.get(0);
        } else {
            return null;
        }
    }

    public String getRoleNameByPrivilegeId(String privilegeId) {
        List<Role> roles = roleDao.getRolesByPrivilegeId(privilegeId);
        String roleNames = "";
        if (roles.size() > 0) {
            roleNames = CollectionHelper.extractToString(roles, "name", ",");
        }
        return roleNames;
    }

    public boolean isExistInRoleByUserId(String userId, String roleNames) {
        boolean result = false;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleNames)) {
            return result;
        }
        List<Role> roles = roleDao.findRolesByUserId(userId);
        for (Role role : roles) {
            List<String> roleNameList = CollectionHelper.stringToList(roleNames);
            if (roleNameList.contains(role.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isExistInUsersByUserId(String userId, String userIds) {
        boolean result = false;
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userIds)) {
            return result;
        }
        List<String> userIdList = CollectionHelper.stringToList(userIds);
        if (userIdList.contains(userId)) {
            result = true;
        }
        return result;
    }

    public String getMailAddressByRoleNames(String roleNames) {
        String result = "";
        if (StringUtils.isNotEmpty(roleNames)) {
            List<String> roleNameList = CollectionHelper.stringToList(roleNames);
            for (String roleName : roleNameList) {
                List<User> users = userDao.getUsersByRoleName(roleName);
                if (CollectionHelper.isNotEmpty(users)) {
                    for (User user : users) {
                        if (StringUtils.isNotEmpty(user.getEmail())) {
                            if (StringUtils.isNotEmpty(result)) {
                                result += ";";
                            }
                            result += user.getEmail();
                        }
                    }
                }
            }
        }
        return result;
    }
}
