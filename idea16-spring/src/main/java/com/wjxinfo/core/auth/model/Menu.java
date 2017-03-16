package com.wjxinfo.core.auth.model;

import com.wjxinfo.core.base.model.AuditableEntity;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by WTH on 2014/5/7.
 */
@Entity
@Table(name = "t_sys_menu")
@JsonIgnoreProperties(value = {"parent", "children", "leaf"})
public class Menu extends AuditableEntity implements Serializable {

    private static final long serialVersionUID = 8756061497411751687L;

    @Column(name = "privilege_identifier")
    private String privilegeIdentifier;

    @Column(name = "menu_name")
    private String name;

    @Column(name = "menu_code")
    private String code;

    @Column(name = "menu_url")
    private String url;

    @Column(name = "menu_desc")
    private String description;

    @Column(name = "menu_icon")
    private String iconCssClass = "icon-list-alt";

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children;

    private Integer seq;

    @Column(length = 1)
    private String status = "1";

    @Transient
    public static void sortList(List<Menu> newList, List<Menu> sourceList, String parentId) {
        for (int i = 0; i < sourceList.size(); i++) {
            Menu e = sourceList.get(i);
            e.setSavedState(null);
            if ((e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)
                    || (parentId == null && e.getParentId() == null))) {
                newList.add(e);
                for (int j = 0; j < sourceList.size(); j++) {
                    Menu child = sourceList.get(j);
                    if (child.getParent() != null && child.getParent().getId() != null
                            && child.getParent().getId().equals(e.getId())) {
                        sortList(newList, sourceList, e.getId());
                        break;
                    }
                }
            }
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrivilegeIdentifier() {

        return privilegeIdentifier;
    }

    public void setPrivilegeIdentifier(String privilegeIdentifier) {
        this.privilegeIdentifier = privilegeIdentifier;
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

    public String getIconCssClass() {
        return iconCssClass;
    }

    public void setIconCssClass(String iconCssClass) {
        this.iconCssClass = iconCssClass;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Transient
    public boolean isTopMenu() {
        return (parent == null);
    }

    @Transient
    public boolean isSubMenu() {
        return (parent != null);
    }

    @Transient
    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        } else {
            return "";
        }
    }

    @Transient
    public String getParentId() {
        if (parent != null) {
            return parent.getId();
        } else {
            return null;
        }
    }

    @Transient
    public void setParentId(String parentId) {
        Menu menu = new Menu();
        if (parentId != null && parentId != "") {
            menu.setId(parentId);
            setParent(menu);
        } else {
            setParent(null);
        }
    }

    @Transient
    public Integer getLevel() {
        if (parent == null) {
            return 0;
        } else {
            return getParentLevel(parent);
        }
    }

    private int getParentLevel(Menu menu) {
        if (menu.getParent() == null) {
            return 1;
        } else {
            return getParentLevel(menu.getParent()) + 1;
        }
    }

    @Transient
    public boolean getLeaf() {
        if (CollectionHelper.isNotEmpty(children)) {
            return false;
        } else {
            return true;
        }
    }

    @Transient
    public boolean getExpanded() {
        return false;
    }

    ;

    @Transient
    public boolean getLoaded() {
        return true;
    }
}
