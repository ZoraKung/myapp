package com.wjxinfo.common.jqgrid.vo;

/**
 * Created by WTH on 2015/10/27.
 */
public class JqGridMappingVo {
    private String gridName;

    private String viewOrTable;

    private Class<?> voClass;

    public JqGridMappingVo(String gridName, String viewOrTable, Class<?> voClass) {
        this.gridName = gridName;
        this.viewOrTable = viewOrTable;
        this.voClass = voClass;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getViewOrTable() {
        return viewOrTable;
    }

    public void setViewOrTable(String viewOrTable) {
        this.viewOrTable = viewOrTable;
    }

    public Class<?> getVoClass() {
        return voClass;
    }

    public void setVoClass(Class<?> voClass) {
        this.voClass = voClass;
    }
}
