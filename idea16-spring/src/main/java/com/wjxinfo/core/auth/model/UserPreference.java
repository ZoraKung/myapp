package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.AuditableEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by WTH on 2014/7/4.
 */
@Entity
@Table(name = "t_sys_user_preference")
public class UserPreference extends AuditableEntity implements Serializable {

    private static final long serialVersionUID = -5388169567798429071L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attr")
    private String attribute; // From UserPreferenceConstants

    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserPreference{" +
                "user=" + user +
                ", attr='" + attribute + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
