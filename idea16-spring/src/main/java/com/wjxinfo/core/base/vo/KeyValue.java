package com.wjxinfo.core.base.vo;

import java.io.Serializable;

/**
 * Created by Walter on 2016/1/29.
 */
public class KeyValue implements Serializable {

    private static final long serialVersionUID = 4699769452105073621L;

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
