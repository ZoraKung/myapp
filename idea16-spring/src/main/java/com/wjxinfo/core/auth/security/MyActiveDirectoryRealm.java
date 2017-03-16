package com.wjxinfo.core.auth.security;

import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.auth.service.UserManager;
import com.wjxinfo.core.auth.utils.cache.UserUtils;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.vo.Principal;
import com.wjxinfo.core.base.web.servlet.CaptchaServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.activedirectory.ActiveDirectoryRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import java.util.List;

/**
 * Created by WTH on 2014/5/13.
 */
public class MyActiveDirectoryRealm extends ActiveDirectoryRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyActiveDirectoryRealm.class);

    private UserManager userManager;

    private Boolean captchaEnable = Boolean.FALSE;

    @Autowired
    public void setUserManager(@Qualifier("userManager") UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken authcToken,
                                                         LdapContextFactory ldapContextFactory)
            throws NamingException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        logger.info("MyActiveDirectoryRealm - queryForAuthenticationInfo :" + token.getUsername());

        // validator captcha
        if (captchaEnable) {
            Session session = SecurityUtils.getSubject().getSession();
            String code = (String) session.getAttribute(CaptchaServlet.CAPTCHA);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
                throw new CaptchaException("Captcha validator error.");
            }
        }

        //User user = userManager.getUserByAdUserName(token.getUsername());
        User user = userManager.getUserByAdUserNameForAuth(token.getUsername());
        if (user != null) {
            Object principal = token.getPrincipal();
            Object credentials = token.getCredentials();

            // Binds using the username and password provided by the user.
            LdapContext ctx = null;
            try {
                // Not work
                //ctx = ldapContextFactory.getLdapContext(principal, String.valueOf(credentials));
                ctx = ldapContextFactory.getLdapContext(principal, credentials);

                Principal masPrincipal = new Principal(user.getId(), user.getLoginName(), user.getUserName(), user.getDisplayName());
                AuthenticationInfo info = new SimpleAuthenticationInfo(masPrincipal, token.getCredentials(), getName());
                return info;
            } finally {
                LdapUtils.closeContext(ctx);
            }
        } else {
            return null;
        }
    }

    /**
     * Get Authorization Info
     *
     * @param principals : Principal Collection
     * @return : Authorization Info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        logger.info("MyActiveDirectoryRealm - doGetAuthorizationInfo :" + principal.getLoginName());

        // User user = userManager.getUserByLoginName(principal.getLoginName());
        //User user = userManager.getUserByAdUserName(principal.getLoginName());
        User user = userManager.getUserByLocalUserNameOrAdUserNameForAuth(principal.getLoginName());

        if (user != null) {
            UserUtils.putCache("user", user);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //Authorization Role
            String roleNames = user.getRoleNames();
            if (StringUtils.isNotBlank(roleNames)) {
                for (String role : roleNames.split(",")) {
                    info.addRole(role);
                }
            }
            //Authorization strings
            List<String> pss = userManager.getPrivilegeStrings(user);
            for (String ps : pss) {
                if (StringUtils.isNotBlank(ps)) {
                    info.addStringPermission(ps);
                }
            }
            return info;
        } else {
            logger.info("MyActiveDirectoryRealm - doGetAuthorizationInfo : not found user - " + principal.getLoginName());
            return null;
        }
    }

// Not work
//    /**
//     * Hash algorithm
//     */
//    @PostConstruct
//    public void initCredentialsMatcher() {
//        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(EncryptUtils.HASH_ALGORITHM);
//        matcher.setHashIterations(EncryptUtils.HASH_INTERATIONS);
//        setCredentialsMatcher(matcher);
//    }

    /**
     * Clean user permission
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
        /*UserUtils.removeCache("user");*/
    }

    /**
     * Clean all permission
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
        UserUtils.removeCache(UserUtils.MAS_MENU_LIST);
    }
}
