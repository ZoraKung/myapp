package com.wjxinfo.common.master.model;


import com.wjxinfo.core.base.model.AuditableEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by WTH on 2014/5/12.
 */
@Entity
@Table(name = "t_bas_master_type")
public class MasterType extends AuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MasterType parent;

    @Column(name = "master_group")
    private String group;

    @NotNull
    @Column(name = "master_type", length = 100, unique = true)
    private String type;

    @Column(name = "master_desc")
    private String description;

    @Column(name = "data_type", length = 20)
    private String dataType = "string";

    private Integer seq;

    @Transient
    private String label;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MasterType getParent() {
        return parent;
    }

    public void setParent(MasterType parent) {
        this.parent = parent;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MasterType{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
