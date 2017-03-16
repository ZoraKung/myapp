package com.wjxinfo.core.auth.security;


import com.wjxinfo.core.auth.model.User;
import com.wjxinfo.core.auth.service.UserManager;
import com.wjxinfo.core.auth.utils.cache.UserUtils;
import com.wjxinfo.core.base.utils.security.EncodeUtils;
import com.wjxinfo.core.base.utils.security.EncryptUtils;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.vo.Principal;
import com.wjxinfo.core.base.web.servlet.CaptchaServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.List;

//import org.hksi.shared.common.audit.utils.RequestLogUtils;

/**
 * Define Shiro Authorization Realm
 */
public class SystemRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(SystemRealm.class);

    private UserManager userManager;

    private Boolean captchaEnable = Boolean.FALSE;

    @Autowired
    public void setUserManager(@Qualifier("userManager") UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Authentication
     *
     * @param authcToken : token
     * @return : Authentication Info
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        logger.info("SystemRealm - doGetAuthenticationInfo :" + token.getUsername());

        // validator captcha
        if (captchaEnable) {
            Session session = SecurityUtils.getSubject().getSession();
            String code = (String) session.getAttribute(CaptchaServlet.CAPTCHA);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
                throw new CaptchaException("Captcha validator error.");
            }
        }

        //User user = userManager.getUserByLoginName(token.getUsername());
        User user = userManager.getUserByLoginNameForAuth(token.getUsername());

        if (user != null) {
            byte[] salt = EncodeUtils.decodeHex(user.getPwd().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(user.getId(), user.getLoginName(), user.getUserName(), user.getDisplayName()),
                    user.getPwd().substring(16), ByteSource.Util.bytes(salt), getName());
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
        logger.info("SystemRealm - doGetAuthorizationInfo :" + principal.getLoginName());

        //save login information
        if (principal != null) {
            //RequestLogUtils.saveLoginInformation("mas", principal.getId(), principal.getLoginName());
            logger.info("need to save login information");
        }

        //User user = userManager.getUserByLoginName(principal.getLoginName());
        User user = userManager.getUserByLoginNameForAuth(principal.getLoginName());
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
            logger.info("SystemRealm - doGetAuthorizationInfo : not found user - " + principal.getLoginName());
            return null;
        }
    }

    /**
     * Hash algorithm
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(EncryptUtils.HASH_ALGORITHM);
        matcher.setHashIterations(EncryptUtils.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

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
