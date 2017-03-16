package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.AuditableEntity;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_sys_privilege")
public class Privilege extends AuditableEntity implements Serializable {

    private static final long serialVersionUID = 5085945153976417889L;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_role_privilege",
            joinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_user_privilege",
            joinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    @Size(max = 100)
    @Column(name = "privilege_module")
    private String module;

    @Size(max = 100)
    @Column(name = "privilege_name")
    private String name;

    @Column(name = "privilege_desc")
    private String desc;

    //Shiro identify
    @Column(name = "privilege_identifier")
    private String identifier;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Transient
    public String getRoleIds() {
        if (CollectionHelper.isNotEmpty(roles)) {
            return CollectionHelper.extractToString(roles, "id", ",");
        } else {
            return "";
        }
    }

    @Transient
    public void setRoleIds(String roleIds) {
        roles = new ArrayList<Role>();
        if (StringUtils.isNotEmpty(roleIds)) {
            for (String id : roleIds.split(",")) {
                Role role = new Role();
                role.setId(id);
                roles.add(role);
            }
        }
    }

    @Transient
    public String getRoleNames() {
        if (CollectionHelper.isNotEmpty(roles)) {
            return CollectionHelper.extractToString(roles, "name", ",");
        } else {
            return "";
        }
    }
}
