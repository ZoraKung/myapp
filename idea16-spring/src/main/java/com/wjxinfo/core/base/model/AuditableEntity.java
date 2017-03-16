package com.wjxinfo.core.base.model;


import com.wjxinfo.core.base.model.utils.AuditableListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditableListener.class})
public abstract class AuditableEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2099103546054756333L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_uid", length = 32)
    private String createBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_up_date")
    private Date lastUpdatedDate;

    @Column(name = "last_up_uid", length = 32)
    private String lastUpdatedBy;

    @Column(name = "delete_flag")
    private Boolean deleteFlag = Boolean.FALSE;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Boolean getDeleteFlag() {
        if (deleteFlag == null) {
            return Boolean.FALSE;
        }

        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        if (deleteFlag == null) {
            this.deleteFlag = Boolean.FALSE;
        } else {
            this.deleteFlag = deleteFlag;
        }
    }
}
