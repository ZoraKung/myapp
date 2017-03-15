package com.demo.idea16.vo;

import java.io.Serializable;

/**
 * Created by Zora on 2017/3/15.
 */
public class TestVo implements Serializable{

    private static final long serialVersionUID = 8173984452000513974L;
    
    private String id;

    private String loginName;
    
    private String displayName;
    
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
