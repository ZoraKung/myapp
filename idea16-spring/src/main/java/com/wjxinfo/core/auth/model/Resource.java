package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by WTH on 2014/5/15.
 */
@Entity
@Table(name = "t_sys_resource")
public class Resource extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1485430357542546988L;

    private String module;

    @Column(name = "resource_desc")
    private String description;

    @NotNull
    private String url;

    @NotNull
    private String permission;

    @Column(name = "plain_flag", length = 1)
    private String plainFlag = "1";

    private Integer seq;

    @Column(length = 1)
    private String status = "1";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPlainFlag() {
        return plainFlag;
    }

    public void setPlainFlag(String plainFlag) {
        this.plainFlag = plainFlag;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "description='" + description + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}
