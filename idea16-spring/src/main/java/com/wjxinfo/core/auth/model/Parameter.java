package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.BaseEntity;
import com.wjxinfo.core.base.utils.common.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_sys_parameter")
public class Parameter extends BaseEntity {
    private static final long serialVersionUID = -4711368972334298166L;

    @NotNull
    private String category = "Default";

    @Column(name = "category_desc")
    private String categoryDesc;

    @NotNull
    private String name;

    @Column(name = "name_desc")
    private String nameDesc;

    @NotNull
    private String value;

    @Column(name = "default_value")
    private String defaultValue;

    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "categoryDesc='" + categoryDesc + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Transient
    public String getParameterName() {
        return (StringUtils.isNotEmpty(nameDesc) ? nameDesc : name);
    }

    @Transient
    public String getParameterCategory() {
        return (StringUtils.isNotEmpty(categoryDesc) ? categoryDesc : category);
    }
}
