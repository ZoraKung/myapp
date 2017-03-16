package com.wjxinfo.core.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Principal User Info
 * Created by Jack on 14-5-12.
 */
public class Principal implements Serializable {
    private static final long serialVersionUID = -7935157755223138369L;

    private String id;

    private String loginName;

    private String name;

    private String fullname;

    private String surname;

    private Map<String, Object> cacheMap;

    public Principal(String userId, String loginName, String userName, String fullname) {
        this.id = userId;
        this.loginName = loginName;
        this.name = userName;
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public String getFullname() {
        return fullname;
    }

    public Map<String, Object> getCacheMap() {
        if (cacheMap == null) {
            cacheMap = new HashMap<String, Object>();
        }
        return cacheMap;
    }
}
