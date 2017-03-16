package com.wjxinfo.core.auth.security;

/**
 * Token Class
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    private String captcha;

    private boolean isLoginAs;

    public UsernamePasswordToken() {
        super();
    }

    public UsernamePasswordToken(String username, char[] password,
                                 boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public UsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    public UsernamePasswordToken(String username, String password, boolean isLoginAs) {
        super(username, password);
        this.isLoginAs = isLoginAs;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isLoginAs() {
        return isLoginAs;
    }

    public void setLoginAs(boolean isLoginAs) {
        this.isLoginAs = isLoginAs;
    }
}