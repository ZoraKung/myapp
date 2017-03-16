package com.wjxinfo.core.base.service;

import com.wjxinfo.core.base.vo.LabelValue;

import java.util.List;

/**
 * Created by WTH on 2014/5/23.
 */
public interface DBUtilsManager {
    long count(String sql);

    int create(String sql);

    int delete(String sql);

    List<LabelValue> getLabelValues(String key);

    List<LabelValue> getLabelValues(String key, String userId);

    List<LabelValue> getLabelValues(String key, String[] args);

    /**
     * @param sql
     * @return List<LabelValue>
     * <p>
     * <b>e.g.</b><br/>
     * String sql = "select col_xxx as label, col_xxx as value from table_xxx [join | left join | ...] ... where criteria"
     * <br/>
     * <strong>Import Note:</strong>
     * the fields of 'label' and 'value' are required.
     * </p>
     */
    List<LabelValue> getLabelValuesBySql(String sql);

    boolean isDuplicated(String key, String pkId, String[] args);

    boolean isPrimaryKeyReferenced(String key, String fkId);

    int update(String sql);

}
