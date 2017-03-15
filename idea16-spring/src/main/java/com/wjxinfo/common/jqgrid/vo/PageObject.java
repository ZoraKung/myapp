package com.wjxinfo.common.jqgrid.vo;

import java.io.Serializable;

/**
 * Author: Jack
 * Date:   14-11-3
 * Description: use in page parameter
 */
public class PageObject implements Serializable {
    private static final long serialVersionUID = -8177517702148539648L;

    private Integer page = 1;
    private Integer rows = 10;
    private String sidx = "id";
    private String sord = "asc";

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
}
