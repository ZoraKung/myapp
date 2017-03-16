package com.wjxinfo.core.auth.utils.cache;

import com.wjxinfo.core.auth.model.Parameter;
import com.wjxinfo.core.auth.service.ParameterManager;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.utils.cache.CacheUtils;
import com.wjxinfo.core.base.vo.LabelValue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Parameter Utils
 */
public class ParameterUtils {
    private static final Logger logger = LoggerFactory.getLogger(ParameterUtils.class);

    private static final String PARAMETER_TYPE = "parameterType";
    private static final String PARAMETER_LIST = "parameterList";

    private static ParameterManager parameterManager = SpringContextHolder.getBean(ParameterManager.class);

    /**
     * Get Parameter Category List
     *
     * @return
     */
    public static List<LabelValue> getCategoryList() {
        List<LabelValue> labelValues = (List<LabelValue>) CacheUtils.get(CacheUtils.SYS_CACHE, PARAMETER_TYPE);
        if (labelValues == null) {
            labelValues = parameterManager.getLabelValueList("categoryDesc", "category");
            CacheUtils.put(PARAMETER_TYPE, labelValues);
        }
        return labelValues;
    }

    /**
     * Get Parameter List by category order
     *
     * @param category
     * @return
     */
    public static List<Parameter> getParameterList(final String category) {
        List<Parameter> list = new ArrayList<Parameter>();
        if (StringUtils.isNotBlank(category)) {
            list = (List<Parameter>) CollectionUtils.select(getParameterList(),
                    new Predicate() {
                        @Override
                        public boolean evaluate(Object o) {
                            Parameter u = (Parameter) o;
                            return category.equals(u.getCategory());
                        }
                    });
        }
        return list;
    }

    /**
     * Get Parameter List
     *
     * @return : List
     */
    public static List<Parameter> getParameterList() {
        List<Parameter> parameterList = (List<Parameter>) CacheUtils.get(CacheUtils.SYS_CACHE, PARAMETER_LIST);
        if (parameterList == null) {
            parameterList = parameterManager.getAll();
            CacheUtils.put(PARAMETER_LIST, parameterList);
        }
        return parameterList;
    }

    public static void removeCache() {
        CacheUtils.remove(CacheUtils.SYS_CACHE, PARAMETER_LIST);
        CacheUtils.remove(CacheUtils.SYS_CACHE, PARAMETER_TYPE);
    }

    public static String getParameter(String parameterName) {
        return getParameter(parameterName, null);
    }

    public static String getCurrentFiscalYear() {
        return parameterManager.getValueByName("CURRENT_FISCAL_YEAR");
    }

    public static String getParameter(String parameterName, List<Parameter> parameters) {
        // if null, search from cache; otherwise use input parameters
        if (parameters == null || parameters.isEmpty()) {
            parameters = getParameterList();
        }
        if (CollectionUtils.isNotEmpty(parameters)) {
            for (Parameter parameter : parameters) {
                if (parameter.getName() != null && parameter.getName().equalsIgnoreCase(parameterName)) {
                    String value = parameter.getValue();
                    if (StringUtils.isEmpty(value)) {
                        value = parameter.getDefaultValue();
                    }

                    return value;
                }
            }
        }

        logger.warn("Parameter [" + parameterName + "] undefined");

        return null;
    }
}
