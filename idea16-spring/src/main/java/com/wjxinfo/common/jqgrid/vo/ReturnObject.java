package com.wjxinfo.common.jqgrid.vo;

/**
 * Created by Jack on 14-5-23.
 */
public class ReturnObject {
    public static final String OK = "OK";
    public static final String ERR = "ERR";
    public static final String SUCCESS = "success";
    private String id;
    private String state = "OK";
    private String message;

    public ReturnObject(String id, String state, String message) {
        this.id = id;
        this.state = state;
        this.message = message;
    }

    public ReturnObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
