package com.wjxinfo.core.base.utils.excel.utils;

import com.wjxinfo.common.master.dao.MasterDao;
import com.wjxinfo.core.base.dao.BaseDao;
import com.wjxinfo.core.base.utils.common.CollectionHelper;
import com.wjxinfo.core.base.utils.common.DateUtils;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.utils.excel.annotation.ExcelField;
import com.wjxinfo.core.base.utils.excel.vo.ReportDataVo;
import com.wjxinfo.core.base.utils.excel.vo.ReportHeaderVo;
import com.wjxinfo.core.base.utils.excel.vo.ReportVo;
import com.wjxinfo.core.base.utils.reflection_properties.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Jack on 16-1-29.
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    private static final MasterDao masterDao = SpringContextHolder.getBean(MasterDao.class);

    public static ReportVo getReportVo(String title, List dataList) {
        ReportVo reportVo = new ReportVo();
        Class<?> clazz = dataList.get(0).getClass();
        List<SortedMap<Integer, ReportHeaderVo>> headerVos = getReportHeader(title, clazz);
        List<SortedMap<Integer, ReportDataVo>> dataVos = getReportData(clazz, dataList);
        reportVo.setHeaderList(headerVos);
        reportVo.setDataList(dataVos);
        return reportVo;
    }

    public static List<SortedMap<Integer, ReportHeaderVo>> getReportHeader(String title, Class<?> clazz) {
        List<SortedMap<Integer, ReportHeaderVo>> headerVos = new ArrayList<SortedMap<Integer, ReportHeaderVo>>();
        SortedMap<Integer, ReportHeaderVo> headerMap = new TreeMap<Integer, ReportHeaderVo>();

        int beginRow = StringUtils.isNotEmpty(title) ? 2 : 1;

        Field[] fields = clazz.getDeclaredFields();
        int row = 0;
        for (Field field : fields) {
            String fieldTitle = StringUtils.capitalize(getDefaultFieldTitle(field.getName()));
            if (field.isAnnotationPresent(ExcelField.class)) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField.hidden()) {
                    continue;
                }
                if (StringUtils.isNotEmpty(excelField.title())) {
                    fieldTitle = excelField.title();
                }
            }
            headerMap.put(row, ReportVo.getReportHeaderVo(fieldTitle, fieldTitle, beginRow, beginRow, 0, 0));
            row++;
        }
        headerVos.add(headerMap);
        return headerVos;
    }

    public static List<SortedMap<Integer, ReportDataVo>> getReportData(Class<?> clazz, List<Class<?>> dataList) {
        List<SortedMap<Integer, ReportDataVo>> dataVos = new ArrayList<SortedMap<Integer, ReportDataVo>>();

        for (int i = 0; i < dataList.size(); i++) {
            SortedMap<Integer, ReportDataVo> dataMap = new TreeMap<Integer, ReportDataVo>();

            Object entity = dataList.get(i);

            Field[] fields = clazz.getDeclaredFields();
            int row = 0;
            for (Field field : fields) {
                String formatter = "";
                int align = 0;
                String fieldName = field.getName();
                if (field.isAnnotationPresent(ExcelField.class)) {
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    if (excelField.hidden()) {
                        continue;
                    }
                    if (StringUtils.isNotEmpty(excelField.format())) {
                        formatter = excelField.format();
                    }
                    if (excelField.align() != 0) {
                        align = excelField.align();
                    }
                }
                Object fieldValue = null;
                try {
                    fieldValue = ReflectionUtils.getFieldValue(entity, fieldName);
                } catch (Exception ex) {
                    logger.error(String.format("Object property [%s] error: %s", fieldName, ex.getMessage()));
                }
                if (fieldValue instanceof java.sql.Date || fieldValue instanceof Date) {
                    if (StringUtils.isEmpty(formatter)) {
                        formatter = DateUtils.YYYY_MM_DD;
                    }
                    if (align == 0) {
                        align = 2;
                    }
                } else if (fieldValue instanceof java.sql.Timestamp) {
                    if (StringUtils.isEmpty(formatter)) {
                        formatter = DateUtils.YYYY_MM_DD_HH_MM_SS;
                    }
                    if (align == 0) {
                        align = 2;
                    }
                } else if ((fieldValue instanceof Integer) || (fieldValue instanceof Long)) {
                    if (StringUtils.isEmpty(formatter)) {
                        formatter = "0";
                    }
                    if (align == 0) {
                        align = 3;
                    }
                } else if (fieldValue instanceof BigDecimal) {
                    if (StringUtils.isEmpty(formatter)) {
                        formatter = "0.00";
                    }
                    if (align == 0) {
                        align = 3;
                    }
                }
                //process master and reflection entity
                if (fieldValue != null) {
                    fieldValue = getLabelByValue(entity, fieldName, fieldValue);
                }
                dataMap.put(row, ReportVo.getReportDataVo(fieldName, fieldValue, align, 0, formatter));

                row++;
            }
            dataVos.add(dataMap);
        }
        return dataVos;
    }

    public static Object getLabelByValue(Object entity, String fieldName, Object fieldValue) {
        String label = "";
        try {
            Field field = ReflectionUtils.getAccessibleField(entity, fieldName);
            if (field != null) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField != null && StringUtils.isNotEmpty(excelField.masterType())) {
                    if (excelField.multiple()) {
                        List<String> values = Arrays.asList(StringUtils.split(fieldValue.toString(), ","));
                        if (CollectionHelper.isNotEmpty(values))
                            label = masterDao.findLabelByTypeAndValues(excelField.masterType(), values);
                    } else {
                        label = masterDao.findLabelByTypeAndValue(excelField.masterType(), fieldValue.toString());
                    }
                } else if (excelField != null && excelField.fieldType() != null
                        && StringUtils.isNotEmpty(excelField.propertyName())
                        ) {
                    String className = StringUtils.uncapitalize(excelField.fieldType().getSimpleName());
                    BaseDao baseDao = SpringContextHolder.getBean(className + "Dao");
                    if (baseDao != null) {
                        Object obj = baseDao.findOne(fieldValue.toString());
                        if (obj != null) {
                            label = (String) ReflectionUtils.getFieldValue(obj, excelField.propertyName());
                        }
                    }

                }
            }
        } catch (Exception ex) {
            logger.error(String.format("Get field value error: %s", ex.getMessage()));
        }
        if (com.wjxinfo.core.base.utils.common.StringUtils.isNotEmpty(label)) {
            return label;
        } else {
            return fieldValue;
        }
    }

    public static String getDefaultFieldTitle(String fieldName) {
        StringBuffer sb = new StringBuffer();
        char[] arr = fieldName.toCharArray();
        for (char ch : arr) {
            if (Character.isUpperCase(ch)) {
                sb.append(" ").append(ch);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
