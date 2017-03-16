package com.wjxinfo.core.auth.utils.cache;

import com.wjxinfo.core.auth.dao.MenuDao;
import com.wjxinfo.core.auth.dao.PrivilegeDao;
import com.wjxinfo.core.auth.dao.UserDao;
import com.wjxinfo.core.auth.model.Menu;
import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.auth.model.UserPreference;
import com.wjxinfo.core.auth.service.UserManager;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.vo.LabelValue;
import com.wjxinfo.core.base.vo.Principal;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserUtils {

    public static final String MAS_MENU_LIST = "masMenuList";
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

    private static PrivilegeDao privilegeDao = SpringContextHolder.getBean(PrivilegeDao.class);

    private static UserManager userManager = SpringContextHolder.getBean(UserManager.class);

    /**
     * Get User
     *
     * @return : User
     */
    public static User getUser() {
        User user = (User) getCache("user");
        if (user == null) {
            Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
            if (principal != null) {
                List<User> users = userDao.findByLoginName(principal.getLoginName());
                if (CollectionHelper.isNotEmpty(users)) {
                    user = users.get(0);
                    putCache("user", users.get(0));
                }
            }
        }
        //Delete User, Logout Security Subject
        if (user == null) {
            user = new User();
            //SecurityUtils.getSubject().logout();
            if (SecurityUtils.getSubject().isAuthenticated()) {
                SecurityUtils.getSubject().logout();
            }
        }
        return user;
    }

    public static String getUserId() {
        User user = getUser();
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }

    public static boolean userHasRole(String role) {
        boolean result = false;
        User user = getUser();
        if (user != null) {

        }
        return result;
    }

    public static String getCurrentUserId() {
        User user = getUser();
        if (user != null && user.getId() != null) {
            return user.getId().toString();
        }
        return "";
    }

    public static String getCurrentUserName() {
        User user = getUser();
        if (user != null) {
            return user.getLoginName();
        } else {
            return "";
        }
    }

    public static String getUserName(String userId) {
        User user = userDao.findOne(userId);
        if (user != null) {
            return user.getLoginName();
        } else {
            return "";
        }
    }

    public static String getUserIdByLoginName(String loginName) {
        List<User> users = userDao.findByLogin(loginName);
        if (users.size() > 0) {
            return users.get(0).getId();
        }
        return null;
    }

    public static List<String> getAllUserLoginName() {
        return CollectionHelper.extractToList(userDao.findAll(), "loginName");
    }

    public static List<User> getAllUsers() {
        return userDao.findAll();
    }

    public static List<LabelValue> getLabelValueList() {
        return userManager.getLabelValueList("loginName", "id");
    }

    public static List<LabelValue> getPrivilegeUserLabelValueList(String privilege) {
        if (StringUtils.isNotEmpty(privilege)) {
            return userManager.getListByPrivilege(privilege);
        } else {
            return userManager.getLabelValueList("loginName", "id");
        }
    }

    /**
     * Get MAS Menu List
     *
     * @return
     */
    public static List<Menu> getMasMenuList() {
        List<Menu> menuList = (List<Menu>) getCache(MAS_MENU_LIST);
        if (menuList == null) {
            menuList = menuDao.findAvailableMenus();

            putCache(MAS_MENU_LIST, menuList);
        }
        return menuList;
    }

    public static Menu getShortCutMenu(String shortCut) {
        logger.info("getUser().getId()=" + getUser().getId());
        User user = userManager.getEntityById(getUser().getId());
        List<Menu> menus = getMasMenuList();
        List<UserPreference> userPreferences = user.getUserPreferences();

        for (UserPreference userPreference : userPreferences) {
            if (shortCut.equals(userPreference.getAttribute())) {
                for (Menu menuCache : menus) {
                    if (null != userPreference.getValue()) {
                        if (userPreference.getValue().equals(menuCache.getId().toString())) {
                            return menuCache;
                        }
                    }

                }
            }
        }
        return null;
    }

    public static Object getCache(String key) {
        Object obj = getCacheMap().get(key);
        return obj == null ? null : obj;
    }

    public static void putCache(String key, Object value) {
        getCacheMap().put(key, value);
    }

    public static void removeCache(String key) {
        getCacheMap().remove(key);
    }

    private static Map<String, Object> getCacheMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            return principal != null ? principal.getCacheMap() : map;
        } catch (UnavailableSecurityManagerException e) {
            return map;
        }
    }
}
