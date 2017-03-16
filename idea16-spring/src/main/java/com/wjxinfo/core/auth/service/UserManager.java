package com.wjxinfo.core.auth.service;

import com.wjxinfo.core.auth.dao.PrivilegeDao;
import com.wjxinfo.core.auth.dao.RoleDao;
import com.wjxinfo.core.auth.dao.UserDao;
import com.wjxinfo.core.auth.model.Privilege;
import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.base.service.BaseManager;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.security.EncryptUtils;
import com.wjxinfo.core.base.vo.LabelValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userManager")
public class UserManager extends BaseManager<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

    @Qualifier("userDao")
    @Autowired
    private UserDao userDao;

    @Qualifier("roleDao")
    @Autowired
    private RoleDao roleDao;

    @Qualifier("privilegeDao")
    @Autowired
    private PrivilegeDao privilegeDao;

    // BT 10722
    public User getUserByLocalUserNameOrAdUserNameForAuth(String loginName) {
        return userDao.findFirstByLocalUserNameOrAdUserNameForAuth(loginName);
    }

    // BT 10722
    public User getUserByAdUserNameForAuth(String loginName) {
        return userDao.findFirstByAdUserNameForAuth(loginName);
    }

    // BT 10722
    public User getUserByLoginNameForAuth(String loginName) {
        return userDao.findFirstByLoginNameForAuth(loginName);
    }

    public User getUserByLoginName(String loginName) {
        List<User> users = userDao.findByLoginName(loginName);
        if (CollectionHelper.isNotEmpty(users)) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User getUserByLoginNameAndPwd(String loginName, String pwd) {
        List<User> users = userDao.findByLoginNameAndPwd(loginName, pwd);
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void updateUserPwd(String userId, String pwd) {
        User user = userDao.findOne(userId);
        if (user != null) {
            user.setPwd(EncryptUtils.e(pwd));
            userDao.save(user);
        }
    }

    public boolean loginNameIsExists(String loginName) {
        logger.debug("login name is exists.");
        List<User> users = userDao.findByLoginName(loginName);
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Privilege> getPrivilegeList(User user) {
        return privilegeDao.getPrivilegeList(user.getId());
    }

    public List<String> getPrivilegeStrings(User user) {
        return privilegeDao.getPrivilegeStrings(user.getId());
    }

    public Boolean getUserIfIsAdmin(String id, Boolean isAdmin) {
        User user = userDao.getUserByIsAdmin(id, isAdmin);
        if (user != null) {
            return isAdmin;
        } else {
            return !isAdmin;
        }
    }

    public List<String> getAllUserLoginName() {
        return CollectionHelper.extractToList(userDao.findAll(), "loginName");
    }


    @Override
    public void remove(String ids) {
        logger.debug("user delete manager ids: " + ids);
        String[] arrIds = ids.split(",");
        if (arrIds.length > 0) {
            for (String id : arrIds) {
                this.remove(getEntityById(id));
            }
        }
    }

    @Override
    public void remove(User user) {
        logger.debug("user delete manager id: " + user.getId());
        if (user != null) {
            user.getRoles().clear();
            userDao.delete(user);
        }
    }

    public User getUserByAdUserName(String adUserName) {
        List<User> users = userDao.findByAdUserName(adUserName);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    public String getUserMailAddress(String userId) {
        String result = "";
        User user = userDao.findOne(userId);
        if (null != user) {
            result = user.getEmail();
        }
        return result;
    }

    public List getListByPrivilege(String privilege) {
        String sql = "select distinct a.local_user_name as label, a.id as value\n" +
                "from\n" +
                "(\n" +
                "select distinct u.local_user_name, u.id \n" +
                "from t_mas_sys_user u, t_mas_sys_role r,\n" +
                "t_mas_sys_user_role ur, t_mas_sys_privilege p,\n" +
                "t_mas_sys_role_privilege rp\n" +
                "where u.id = ur.user_id and r.id = ur.role_id\n" +
                "and r.id = rp.role_id and p.id = rp.privilege_id\n" +
                "and (r.role_name='ROLE_ADMIN' or p.privilege_identifier = '%s')\n" +
                "UNION\n" +
                "select DISTINCT u.local_user_name, u.id\n" +
                "from t_mas_sys_user u, t_mas_sys_privilege p, \n" +
                "t_mas_sys_user_privilege up\n" +
                "where u.id = up.user_id and p.id = up.privilege_id\n" +
                "and p.privilege_identifier = '%s'\n" +
                ") a order by a.local_user_name";
        return getSQLQueryList(String.format(sql, privilege, privilege), LabelValue.class);
    }
}
