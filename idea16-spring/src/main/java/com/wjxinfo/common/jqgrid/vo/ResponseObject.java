package com.wjxinfo.common.jqgrid.vo;

import java.io.Serializable;
import java.util.List;

public class ResponseObject<T extends Serializable> {
    private String page;     //current page

    private String total;    //total page

    private String records;  //total recored

    private List<T> rows;    //data list

    private ResponseStatus userData; //response status

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public ResponseStatus getUserData() {
        return userData;
    }

    public void setUserData(ResponseStatus userData) {
        this.userData = userData;
    }
}
