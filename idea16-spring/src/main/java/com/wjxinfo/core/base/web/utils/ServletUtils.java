package com.wjxinfo.core.base.web.utils;

import com.wjxinfo.core.base.utils.common.CollectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.util.*;

public class ServletUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * Get Map From Request
     *
     * @param request
     * @param prefix
     * @return
     */
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);


                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, CollectionHelper.convertToString(Arrays.asList(values), ","));
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    public static Map<String, Object> getParameters(ServletRequest request) {
        return ServletUtils.getParametersStartingWith(request, "search_");
    }

    /**
     * Get Page Parameter Map
     *
     * @param request
     * @param pageNo   : default value
     * @param pageSize : default value
     * @return
     */
    public static Map<String, Object> getPageParameters(ServletRequest request, int pageNo, int pageSize, String sortName, String sortOrder) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (request.getParameter("pageSize") != null
                && !"".equals(request.getParameter("pageSize"))) {
            map.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
        } else {
            map.put("pageSize", pageSize);
        }
        if (request.getParameter("page") != null
                && !"".equals(request.getParameter("page"))) {
            map.put("pageNo", Integer.parseInt(request.getParameter("page")));
        } else {
            map.put("pageNo", pageNo);
        }
        if (request.getParameter("sort") != null
                && !"".equals(request.getParameter("sort"))) {
            map.put("sortName", request.getParameter("sort"));
        } else {
            map.put("sortName", sortName);
        }
        if (request.getParameter("dir") != null
                && !"".equals(request.getParameter("dir"))) {
            map.put("sortOrder", request.getParameter("dir"));
        } else {
            map.put("sortOrder", sortOrder);
        }
        return map;
    }

    public static Map<String, Object> getPageParameters(ServletRequest request) {
        return ServletUtils.getPageParameters(request, 1, 10, "", "asc");
    }

    public static Map<String, Object> getPageIdsParameters(ServletRequest request) {
        Map<String, Object> ids = new HashMap<String, Object>();
        if (request.getParameterValues("rowId") != null && !"".equals(request.getParameterValues("rowId"))) {
            String[] arrIds = request.getParameterValues("rowId");
            for (String param : arrIds) {
                if (StringUtils.isNotEmpty(param)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("checked", false);
                    ids.put(param.trim(), map);
                }
            }
        }
        if (request.getParameterValues("checked") != null && !"".equals(request.getParameterValues("checked"))) {
            String[] arrSelectedIds = request.getParameterValues("checked");
            for (String param : arrSelectedIds) {
                String[] arrParams = param.split("\\|");
                if (arrParams.length > 0 && StringUtils.isNotEmpty(arrParams[0]) && ids.containsKey(arrParams[0].trim())) {
                    ids.remove(arrParams[0].trim().trim());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("checked", true);
                    if (arrParams.length > 1) {
                        map.put("name", param.trim());
                    }
                    ids.put(arrParams[0].trim(), map);
                }
            }
        }
        return ids;
    }

    public static Map<String, Object> getGridParameters(ServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (request.getParameter("_search") != null) {
            map.put("search", Boolean.parseBoolean(request.getParameter("_search")));
        } else {
            map.put("search", false);
        }
        if (request.getParameter("filters") != null && !"".equals(request.getParameter("filters"))) {
            map.put("filters", Integer.parseInt(request.getParameter("filters")));
        } else {
            map.put("filters", "");
        }
        if (request.getParameter("page") != null && !"".equals(request.getParameter("page"))) {
            map.put("pageNo", Integer.parseInt(request.getParameter("page")));
        } else {
            map.put("pageNo", 1);
        }
        if (request.getParameter("rows") != null && !"".equals(request.getParameter("rows"))) {
            map.put("pageSize", Integer.parseInt(request.getParameter("rows")));
        } else {
            map.put("pageSize", 10);
        }
        if (request.getParameter("sidx") != null && !"".equals(request.getParameter("sidx"))) {
            map.put("sortName", request.getParameter("sidx"));
        } else {
            map.put("sortName", "");
        }
        if (request.getParameter("sord") != null
                && !"".equals(request.getParameter("sord"))) {
            map.put("sortOrder", request.getParameter("sord"));
        } else {
            map.put("sortOrder", "");
        }
        return map;
    }
}
