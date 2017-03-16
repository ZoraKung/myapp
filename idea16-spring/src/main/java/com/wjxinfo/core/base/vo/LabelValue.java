package com.wjxinfo.core.base.vo;

import java.io.Serializable;

public class LabelValue implements Serializable {
    private static final long serialVersionUID = -6851878901568908047L;

    private String label;

    private String value;

    /**
     * @param label
     * @param value
     */
    public LabelValue(String label, String value) {
        super();
        this.label = label;
        this.value = value;
    }

    /**
     *
     */
    public LabelValue() {
        super();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LabelValue [label=" + label + ", value=" + value + "]";
    }


}
