package com.wjxinfo.core.base.web.utils;

import com.wjxinfo.core.base.utils.common.DateUtils;
import com.wjxinfo.core.base.utils.common.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search Filter
 */
public class SearchFilter {
    public String fieldName;

    public Object value;

    public Operator operator;

    /**
     * Search Filter
     *
     * @param fieldName : field name
     * @param operator  : operator
     * @param value     : field value
     */
    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * Parse search parameters to search filter map
     *
     * @param searchParams : search parameters
     * @return : search filter map
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();

        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || (value != null && value instanceof String && StringUtils.isBlank((String) value))) {
                continue;
            }

            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            SearchFilter filter;
            if ((Operator.NI.equals(operator) || Operator.IN.equals(operator)) && (value instanceof String)) {
                List<String> listValue = StringUtils.stringToList(value.toString(), ",");
                filter = new SearchFilter(filedName, operator, listValue);
            } else if (!(value instanceof String)) {
                filter = new SearchFilter(filedName, operator, value);
            } else {
                Date dateValue = null;
                /*if (value.toString().length() <= 5) {
                    dateValue = DateUtils.formatTimeNoSs(value.toString());
                } else */
                if (value.toString().length() <= 8) {
                    dateValue = DateUtils.formatTime(value.toString());
                } else if (value.toString().length() <= 10) {
                    dateValue = DateUtils.formatNoTime(value.toString());
                } else if (value.toString().length() <= 16) {
                    dateValue = DateUtils.formatWithHHmm(value.toString());
                } else {
                    dateValue = DateUtils.format(value.toString());
                }
                if (dateValue != null) {
                    filter = new SearchFilter(filedName, operator, dateValue);
                } else {
                    filter = new SearchFilter(filedName, operator, value);
                    /*Long longValue = null;
                    try {
                        longValue = Long.parseLong(value.toString());
                        filter = new SearchFilter(filedName, operator, longValue);
                    } catch (RuntimeException e) {
                        filter = new SearchFilter(filedName, operator, value);
                    }*/

                }
            }
            filters.put(key, filter);
        }
        return filters;
    }

    public enum Operator {
        EQ, NE, GT, LT, GE, LE, BW, BN, EW, EN, CN, NC, IN, NI
    }
}
