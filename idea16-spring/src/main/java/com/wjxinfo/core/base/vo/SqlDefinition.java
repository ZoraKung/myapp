package com.wjxinfo.core.base.vo;

import java.util.HashMap;
import java.util.Map;

public class SqlDefinition {
    private Map<String, String> labelValueSql = new HashMap<String, String>();

    private Map<String, String> preDefinedSql = new HashMap<String, String>();

    public Map<String, String> getPreDefinedSql() {
        return preDefinedSql;
    }

    public void setPreDefinedSql(Map<String, String> preDefinedSql) {
        this.preDefinedSql = preDefinedSql;
    }

    public Map<String, String> getLabelValueSql() {
        return labelValueSql;
    }

    public void setLabelValueSql(Map<String, String> labelValueSql) {
        this.labelValueSql = labelValueSql;
    }
}
