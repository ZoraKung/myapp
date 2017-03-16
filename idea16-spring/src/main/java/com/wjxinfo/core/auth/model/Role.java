package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.AuditableEntity;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_sys_role")
@JsonIgnoreProperties(value = {"privileges", "users"})
public class Role extends AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_role_privilege",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private List<Privilege> privileges;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_user_role",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> users;

    @NotNull
    @Size(max = 100)
    @Column(name = "role_name")
    private String name;

    @Column(name = "role_desc", nullable = true, length = 255)
    private String description;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdministrator = Boolean.FALSE;

    public Boolean getAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(Boolean administrator) {
        isAdministrator = administrator;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAdministrator() {
        return isAdministrator;
    }

    public void setIsAdministrator(Boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    @Override
    public String toString() {
        return "Role{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", isAdministrator=" + isAdministrator +
                '}';
    }

    @Transient
    public String getPrivilegeNames() {
        if (CollectionHelper.isNotEmpty(privileges)) {
            return CollectionHelper.extractToString(privileges, "name", ",");
        } else {
            return "";
        }
    }

    @Transient
    public String getPrivilegeIds() {
        if (CollectionHelper.isNotEmpty(privileges)) {
            return CollectionHelper.extractToString(privileges, "id", ",");
        } else {
            return "";
        }
    }

    //Set Privilege Role
    @Transient
    public void setPrivilegeIds(String roleIds) {
        privileges = new ArrayList<Privilege>();
        if (StringUtils.isNotEmpty(roleIds)) {
            for (String id : roleIds.split(",")) {
                Privilege privilege = new Privilege();
                privilege.setId(id);
                privileges.add(privilege);
            }
        }
    }

    @Transient
    public String getUserNames() {
        if (CollectionHelper.isNotEmpty(users)) {
            return CollectionHelper.extractToString(users, "loginName", ",");
        } else {
            return "";
        }
    }

    @Transient
    public String getUserIds() {
        if (CollectionHelper.isNotEmpty(users)) {
            return CollectionHelper.extractToString(users, "id", ",");
        } else {
            return "";
        }
    }

    //Set Privilege User
    @Transient
    public void setUserIds(String userIds) {
        users = new HashSet<User>();
        if (StringUtils.isNotEmpty(userIds)) {
            for (String id : userIds.split(",")) {
                User user = new User();
                user.setId(id);
                users.add(user);
            }
        }
    }
}
