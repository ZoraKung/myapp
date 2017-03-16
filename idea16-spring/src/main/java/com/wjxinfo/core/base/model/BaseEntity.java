package com.wjxinfo.core.base.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

//import javax.persistence.*;


//@DynamicInsert(value = true)
//@DynamicUpdate(value = true)
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable {
    private static final long serialVersionUID = -5123954545827969124L;


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 32)
    private String id;

//    @Id
//    @Column(name = "id", length = 32)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "FormatTableGenerator")
//    @GenericGenerator(name = "FormatTableGenerator", strategy = "org.hksi.shared.base.model.utils.FormatTableGenerator", parameters = {
//            @org.hibernate.annotations.Parameter(name = "format", value = "%1$05d"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.TABLE_PARAM, value = "t_sys_id_gen"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.SEGMENT_COLUMN_PARAM, value = "gen_key"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.VALUE_COLUMN_PARAM, value = "gen_value"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.INITIAL_PARAM, value = "1"),
//            @org.hibernate.annotations.Parameter(name = TableGenerator.INCREMENT_PARAM, value = "1"),
//    })
//    private String id;

    @Transient
    private boolean checked = false;

    @Transient
    private transient BaseEntity savedState;

    @JsonIgnore
    public BaseEntity getSavedState() {
        return savedState;
    }

    public void setSavedState(BaseEntity savedState) {
        this.savedState = savedState;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }

        BaseEntity other = (BaseEntity) obj;
        return getId().equals(other.getId());
    }

    @Override
    public BaseEntity clone() {
        BaseEntity o = null;
        try {
            o = (BaseEntity) super.clone();
            o.setId(null);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
