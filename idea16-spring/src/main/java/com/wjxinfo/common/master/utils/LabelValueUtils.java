package com.wjxinfo.common.master.utils;

import com.wjxinfo.common.master.service.MasterManager;
import com.wjxinfo.core.base.constants.Constants;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.cache.CacheUtils;
import com.wjxinfo.core.base.vo.LabelValue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LabelValue Utils
 */
public class LabelValueUtils {

    private static final String LABEL_VALUE_BY_TYPE = "labelValueByType_";

    private static final String LABEL_VALUE_BY_TYPE_AND_PARENT = "labelValueByTypeAndParent_";

    private static final String NIP_LABEL_VALUE_BY_TYPE_AND_PARENT = "nipLabelValueByTypeAndParent_";

    @SuppressWarnings("unchecked")
    private static MasterManager masterManager = SpringContextHolder.getBean(MasterManager.class);

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getLabelValueList(final String type) {
        @SuppressWarnings("unchecked")
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE + type);
        if (labelValues == null || (labelValues != null && labelValues.size() == 0) || ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            labelValues = masterManager.getLabelValueList(type);
            CacheUtils.put(LABEL_VALUE_BY_TYPE + type, labelValues);
        }
        return labelValues;
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getLabelValueList(final String type, final String filter) {
        @SuppressWarnings("unchecked")
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE + type);
        if (labelValues == null || (labelValues != null && labelValues.size() == 0) || ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            labelValues = masterManager.getLabelValueList(type);
            CacheUtils.put(LABEL_VALUE_BY_TYPE + type, labelValues);
        }
        //process filter
        if (StringUtils.isNotEmpty(filter) && CollectionHelper.isNotEmpty(labelValues)) {
            String[] filters = filter.split(";");
            List<LabelValue> labelValueList = new ArrayList<>();
            for (LabelValue lv : labelValues) {
                if (!Arrays.asList(filters).contains(lv.getValue())) {
                    labelValueList.add(lv);
                }
            }
            return labelValueList;
        } else {
            return labelValues;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getLabelValueListWithoutCache(final String type) {
        return masterManager.getLabelValueList(type);
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getFullLabelValueListWithoutCache(final String type) {
        return masterManager.getFullLabelValueList(type);
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getCascadeLabelValueList(final String type, final String parentValue) {
        @SuppressWarnings("unchecked")
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE_AND_PARENT + type + "_" + parentValue);
        if (labelValues == null) {
            labelValues = masterManager.getCascadeLabelValueList(type, parentValue);
            CacheUtils.put(LABEL_VALUE_BY_TYPE_AND_PARENT + type + "_" + parentValue, labelValues);
        }
        return labelValues;
    }

    public static List<LabelValue> getCascadeLabelValueListWithoutCache(final String type, final String parentValue) {
        return masterManager.getCascadeLabelValueList(type, parentValue);
    }

    public static List<LabelValue> getCascadeFullLabelValueListWithoutCache(final String type, final String parentValue) {
        return masterManager.getCascadeFullLabelValueList(type, parentValue);
    }

    public static List<LabelValue> getLabelValueListByFilter(final String type, final String filter) {
        return masterManager.getLabelValueListByFilter(type, filter);
    }

    public static List<LabelValue> getLabelListByFilter(final String type, final String filter) {
        return masterManager.getLabelListByFilter(type, filter);
    }

    //bt:12657
    public static List<LabelValue> getLabelValueListByValues(final String type, final String value) {
        return masterManager.getLabelValueListByValues(type, value);
    }

    public static void removeCache(String type, String parentValue) {
        CacheUtils.remove(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE + type);
        CacheUtils.remove(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE_AND_PARENT + type + "_" + parentValue);
    }


    public static String getValueByLabel(String label, List<LabelValue> list) {
        if (label == null || label.isEmpty() || list == null || list.isEmpty()) {
            return null;
        }
        for (LabelValue lv : list) {
            if (label.equals(lv.getLabel())) {
                return lv.getValue();
            }
        }
        return null;
    }

    public static String getLabelByValue(String value, List<LabelValue> list) {
        if (value == null || value.isEmpty() || list == null || list.isEmpty()) {
            return null;
        }
        for (LabelValue lv : list) {
            if (value.equals(lv.getValue())) {
                return lv.getLabel();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List<LabelValue> getAutoCompleteList(final String type, final String term) {
        @SuppressWarnings("unchecked")
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, LABEL_VALUE_BY_TYPE + type);
        if (labelValues == null || (labelValues != null && labelValues.size() == 0) || ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            labelValues = masterManager.getLabelValueListByTerm(type, term);
            CacheUtils.put(LABEL_VALUE_BY_TYPE + type, labelValues);
        } else if (CollectionUtils.isNotEmpty(labelValues) && StringUtils.isNotBlank(term)) {
            labelValues = masterManager.getLabelValueListByTerm(labelValues, term);
        }
        return labelValues;
    }

    public static List<LabelValue> getNipCascadeLabelValueList (final String type, final String parentValue) {
        @SuppressWarnings("unchecked")
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, NIP_LABEL_VALUE_BY_TYPE_AND_PARENT + type + "_" + parentValue);
        if (labelValues == null || (labelValues != null && labelValues.size() == 0)
                && ArrayUtils.contains(Constants.SPECIFIED_LABEL_VALUE, type)) {
            labelValues = masterManager.getCascadeLabelValueListBySqlXml(type, parentValue);
            CacheUtils.put(NIP_LABEL_VALUE_BY_TYPE_AND_PARENT + type + "_" + parentValue, labelValues);
        }
        return labelValues;
    }
}
