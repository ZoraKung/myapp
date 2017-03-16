package com.wjxinfo.common.master.model;

import com.wjxinfo.core.base.model.AuditableEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by WTH on 2014/5/12.
 */
@Entity
@Table(name = "t_bas_master")
//@AttributeOverride(name = "id", column = @Column(name = "id", length = 32))
public class Master extends AuditableEntity implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Master parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Master> children;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_type_id")
    private MasterType masterType;

    @NotNull
    @Column(columnDefinition = "nvarchar(255)")
    private String label;

    @NotNull
    @Column(columnDefinition = "nvarchar(255)")
    private String value;

    @Column(name = "additional_value", columnDefinition = "nvarchar(255)")
    private String additionalValue;

    @Column(name = "additional_value2", columnDefinition = "nvarchar(255)")
    private String additionalValue2;

    @Column(name = "additional_value3", columnDefinition = "nvarchar(255)")
    private String additionalValue3;

    private Integer seq;

    // 0:Disable / 1:Enable
    @Column(length = 1)
    private String status;
    //TODO Y / N
    //can be edit in UI
    @Column(length = 1)
    private String editable;
    //TODO Y / N
    //can be delete in UI
    @Column(length = 1)
    private String deletable;

    public String getAdditionalValue() {
        return additionalValue;
    }

    public void setAdditionalValue(String additionalValue) {
        this.additionalValue = additionalValue;
    }

    public List<Master> getChildren() {
        return children;
    }

    public void setChildren(List<Master> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MasterType getMasterType() {
        return masterType;
    }

    public void setMasterType(MasterType masterType) {
        this.masterType = masterType;
    }

    /**
     * For Grid Display
     *
     * @return
     */
    @Transient
    public String getParentId() {
        if (this.getParent() != null) {
            return this.getParent().getId();
        }

        return null;
    }

    public Master getParent() {
        return parent;
    }

    public void setParent(Master parent) {
        this.parent = parent;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getDeletable() {
        return deletable;
    }

    public void setDeletable(String deletable) {
        this.deletable = deletable;
    }

    public String getAdditionalValue2() {
        return additionalValue2;
    }

    public void setAdditionalValue2(String additionalValue2) {
        this.additionalValue2 = additionalValue2;
    }

    public String getAdditionalValue3() {
        return additionalValue3;
    }

    public void setAdditionalValue3(String additionalValue3) {
        this.additionalValue3 = additionalValue3;
    }

    @Override
    public String toString() {
        return "Master{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
