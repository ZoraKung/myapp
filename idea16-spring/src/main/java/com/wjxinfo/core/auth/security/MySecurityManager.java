package com.wjxinfo.core.auth.security;

import org.apache.shiro.session.Session;

import java.util.HashMap;
import java.util.Map;

//@Service("securityManager")
public class MySecurityManager extends org.apache.shiro.web.mgt.DefaultWebSecurityManager {

    Map<String, Session> map = new HashMap();

    public Map<String, Session> getMap() {
        return map;
    }

    public void setMap(Map<String, Session> map) {
        this.map = map;
    }


}
