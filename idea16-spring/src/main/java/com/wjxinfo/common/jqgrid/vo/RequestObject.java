package com.wjxinfo.common.jqgrid.vo;

public class RequestObject {
    //excel, pdf
    private String fileType;

    //1, 0, -1
    private String isDirection;
    //selected ids
    private String idsOfSelectedRows;

    private String gridName;

    private String caption;

    private Boolean _search;

    private String filters;

    private Integer page;

    private Integer rows;

    private String sidx;

    private String sord;

    private String fields;

    public String getIdsOfSelectedRows() {
        return idsOfSelectedRows;
    }

    public void setIdsOfSelectedRows(String idsOfSelectedRows) {
        this.idsOfSelectedRows = idsOfSelectedRows;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getIsDirection() {
        return isDirection;
    }

    public void setIsDirection(String isDirection) {
        this.isDirection = isDirection;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public Boolean get_search() {
        return _search;
    }

    public void set_search(Boolean _search) {
        this._search = _search;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
