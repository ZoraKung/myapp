package com.wjxinfo.common.master.service;

import com.wjxinfo.common.master.dao.MasterTypeDao;
import com.wjxinfo.common.master.model.MasterType;
import com.wjxinfo.core.base.service.BaseManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("masterTypeManager")
public class MasterTypeManager extends BaseManager<MasterType> {
    @Qualifier("masterTypeDao")
    @Autowired
    private MasterTypeDao masterTypeDao;

    public MasterType findByType(String type) {

        List<MasterType> masterTypes = this.masterTypeDao.findByType(type);
        if (null != masterTypes && masterTypes.size() > 0) {
            return masterTypes.get(0);
        }
        return null;
    }

    public boolean isDuplicateType(String type, String id) {
        if (StringUtils.isNotBlank(id)) {
            //edit
            List<MasterType> masterTypes = this.masterTypeDao.findByType(type);
            if (null == masterTypes || masterTypes.size() == 0) {
                return false;
            }
            if (masterTypes.size() > 1) {
                return true;
            }
            if (masterTypes.size() == 1) {
                String entityId = masterTypes.get(0).getId();
                if (id.equals(String.valueOf(entityId))) {
                    return false;
                } else {
                    return true;
                }
            }

        } else {
            //create
            return null != findByType(type);

        }
        return false;
    }
}
