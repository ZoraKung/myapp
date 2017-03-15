package com.wjxinfo.common.jqgrid.vo;

/**
 * Created by Jack on 14-5-14.
 */
public class FieldObject {
    private String name;

    private String title;

    private Integer width;

    private boolean hidden = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
