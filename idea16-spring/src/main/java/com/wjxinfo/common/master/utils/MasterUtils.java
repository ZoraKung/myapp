package com.wjxinfo.common.master.utils;

import com.wjxinfo.common.master.model.Master;
import com.wjxinfo.common.master.model.MasterType;
import com.wjxinfo.common.master.service.MasterManager;
import com.wjxinfo.common.master.service.MasterTypeManager;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.utils.cache.CacheUtils;
import com.wjxinfo.core.base.vo.LabelValue;

import java.util.List;

/**
 * Master Utils
 */
public class MasterUtils {

    private static final String MASTER_LIST_BY_TYPE = "masterByType_";
    private static final String ALL_MASTER_TYPE = "allMasterType";
    private static final String MASTER_TYPE_LIST = "masterTypes";

    private static MasterTypeManager masterTypeManager = SpringContextHolder.getBean(MasterTypeManager.class);
    private static MasterManager masterManager = SpringContextHolder.getBean(MasterManager.class);

    /**
     * Get Master Type --> LabelValue
     *
     * @return
     */
    public static List<LabelValue> getMasterTypeList() {
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, MASTER_TYPE_LIST);
        if (labelValues == null) {
            labelValues = masterTypeManager.getLabelValueList("description", "type");
            CacheUtils.put(MASTER_TYPE_LIST, labelValues);
        }
        return labelValues;
    }

    /**
     * Get Master List by master type
     *
     * @param masterType
     * @return
     */
    public static List<Master> getMasterListByType(final String masterType) {
        List<Master> masterList = (List<Master>) CacheUtils.get(CacheUtils.SYS_CACHE, MASTER_LIST_BY_TYPE + masterType);
        if (masterList == null) {
            masterList = masterManager.findByMasterType(masterType);
            CacheUtils.put(MASTER_LIST_BY_TYPE + masterType, masterList);
        }
        return masterList;
    }

    public static String getMasterValueByTypeAndLabel(String masterType, String label) {
        return masterManager.getValueByTypeAndLabel(masterType, label);
    }

    public static String getAdditionalValueByTypeAndValue(String type, String value, Integer seq) {
        return masterManager.getAdditionalValueByTypeAndValue(type, value, seq);
    }

    /**
     * Get Master Type List
     *
     * @return : List
     */
    public static List<MasterType> getAllMasterTypes() {
        List<MasterType> masterTypeList = (List<MasterType>) CacheUtils.get(CacheUtils.SYS_CACHE, ALL_MASTER_TYPE);
        if (masterTypeList == null) {
            masterTypeList = masterTypeManager.getAll();
            CacheUtils.put(ALL_MASTER_TYPE, masterTypeList);
        }
        return masterTypeList;
    }

    public static void removeCache(String masterType) {
        CacheUtils.remove(CacheUtils.SYS_CACHE, ALL_MASTER_TYPE);
        CacheUtils.remove(CacheUtils.SYS_CACHE, MASTER_LIST_BY_TYPE + masterType);
        CacheUtils.remove(CacheUtils.SYS_CACHE, MASTER_TYPE_LIST);
    }
}
