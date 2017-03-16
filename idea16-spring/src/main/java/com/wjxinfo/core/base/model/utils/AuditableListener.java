package com.wjxinfo.core.base.model.utils;

import com.wjxinfo.core.base.model.AuditableEntity;
import com.wjxinfo.core.base.vo.Principal;
import org.apache.shiro.SecurityUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Jack on 14-6-5.
 */
public class AuditableListener {
    private static Principal getPrincipal() {
        try {
            Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @PreUpdate
    @PrePersist
    private void preUpdate(AuditableEntity auditableEntity) {
        Principal principal = getPrincipal();
        if (auditableEntity.getId() == null) {
            auditableEntity.setCreateDate(new Date());
            auditableEntity.setLastUpdatedDate(new Date());
            if (principal != null) {
                auditableEntity.setCreateBy(principal.getId());
                auditableEntity.setLastUpdatedBy(principal.getId());
            }
            // Default
            if (auditableEntity.getDeleteFlag() == null) {
                auditableEntity.setDeleteFlag(Boolean.FALSE);
            }
        } else {
            if (principal != null) {
                if (auditableEntity.getCreateBy() == null) {
                    auditableEntity.setCreateBy(principal.getId());
                }
                auditableEntity.setLastUpdatedBy(principal.getId());
            }
            if (auditableEntity.getCreateDate() == null) {
                auditableEntity.setCreateDate(new Date());
            }
            auditableEntity.setLastUpdatedDate(new Date());

            // Default
            if (auditableEntity.getDeleteFlag() == null) {
                auditableEntity.setDeleteFlag(Boolean.FALSE);
            }
        }
    }
}
