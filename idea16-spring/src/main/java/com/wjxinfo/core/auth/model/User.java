package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.constants.Constants;
import com.wjxinfo.core.base.model.AuditableEntity;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "t_sys_user")
@JsonIgnoreProperties(value = {"roles", "privileges", "userPreferences"})
public class User extends AuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    // Login ID of Local User
    @Column(name = "local_user_name", nullable = false, length = 100)
    private String loginName;

    // User Name
    @NotNull(message = "Display name is required.")
    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    // Email
    @Column(name = "email", nullable = false)
    private String email;

    // Password
    @Column(name = "pwd")
    private String pwd;

    // Active,  Expired – After account expiry date,
    // Lock – Account is locked after 5 times login fail, Suspended – Suspend the account by user
    @Column(name = "status")
    private String status = Constants.STATUS_ENABLE;

    // Login ID of AD user
    @Column(name = "ad_user_name")
    private String adUserName;

    @Deprecated
    @Column(name = "action_hash")
    private String actionHash;

    @Deprecated
    @Column(name = "password_hash")
    private String passwordHash;

    @Deprecated
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Deprecated
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "user_type", length = 20)
    private String userType = Constants.USER_TYPE_LOCAL;

    // User Profile
    @JsonIgnore
    private String avatar;

    // User Profile - Document ID
    @JsonIgnore
    @Column(name = "avatar_id")
    private String avatarId;

    // New : FDS
    @Column(length = 10)
    private String grade;

    @Column(length = 20)
    private String title;

    // FK: Department ?
//    @Column(length = 255)
//    private String department;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "extension_number", length = 5)
    private String extensionNumber;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "t_sys_user_privilege",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private List<Privilege> privileges;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPreference> userPreferences;

    @Column(name = "staff_no", length = 20)
    private String staffNo;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "dept_name", length = 255)
    private String deptName;

    @Column(name = "hired")
    private Boolean hired = Boolean.TRUE;

    @Column(name = "phone", length = 32)
    private String phone;

    @Column(name = "area_id", length = 32)
    private String areaId;

    @Column(name = "director_id", length = 32)
    private String directorId;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getHired() {
        return hired;
    }

    public void setHired(Boolean hired) {
        this.hired = hired;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getActionHash() {
        return actionHash;
    }

    public void setActionHash(String actionHash) {
        this.actionHash = actionHash;
    }

    public String getAdUserName() {
        return adUserName;
    }

    public void setAdUserName(String adUserName) {
        this.adUserName = adUserName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
    public String getPrivilegeNames() {
        if (CollectionHelper.isNotEmpty(privileges)) {
            return CollectionHelper.extractToString(privileges, "name", ",");
        } else {
            return "";
        }
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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
        roles = new HashSet<Role>();
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    public String getUserName() {
        if (StringUtils.isNotEmpty(displayName)) {
            return displayName;
        } else {
            return loginName;
        }
        // return "[" + loginName + "]" + (StringUtils.isNotEmpty(displayName) ? " " + displayName : "");
    }

    public List<UserPreference> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<UserPreference> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "loginName='" + loginName + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
