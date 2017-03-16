package com.wjxinfo.core.base.utils.common;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static Boolean stringToBoolean(String source) {
        if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
            return Boolean.FALSE;
        }
        if ("1".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }
        if ("Y".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }
        if ("YES".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }
        if ("T".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }
        if ("TRUE".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * replace html tag
     */
    public static String replaceHtml(String html) {
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * Return List from string
     *
     * @param strList   : string
     * @param separator : separator
     * @return : List
     */
    public static List<Long> stringToLongList(String strList, String separator) {
        List<Long> list = new ArrayList<Long>();
        String[] arrString = strList.split(separator);
        for (String str : arrString) {
            list.add(Long.parseLong(str));
        }
        return list;
    }

    /**
     * Return String List
     *
     * @param strList   : string
     * @param separator : separator
     * @return : List
     */
    public static List<String> stringToList(String strList, String separator) {
        List<String> list = Arrays.asList(strList.split(separator));
        return list;
    }

    /**
     * Return Date List
     *
     * @param strList   : string
     * @param separator : separator
     * @return : List
     */
    public static List<Date> stringToDateList(String strList, String separator) {
        List<Date> list = new ArrayList<Date>();
        String[] arrString = strList.split(separator);
        for (String str : arrString) {
            list.add(DateUtils.format(str));
        }
        return list;
    }

    /**
     * Get remote client ip address
     *
     * @param request
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String remoteAddress = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddress)) {
            remoteAddress = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteAddress)) {
            remoteAddress = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteAddress)) {
            remoteAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddress != null ? remoteAddress : request.getRemoteAddr();
    }

    /**
     * @param num    - value of String
     * @param digits - total number of digits
     * @return String with leading zero
     * E.g. @return: 0010, @param num=10, digits=4
     */
    public static String getNumberWithLeadingZero(int num, int digits) {
        String format = String.format("%%0%dd", digits);
        String result = String.format(format, num);
        return result;
    }

    public static String convertStringToListString(String ids) {
        String[] idList = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (String id : idList) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(sb.toString())) {
                sb.append(",");
            }
            sb.append("'").append(id).append("'");
        }
        return sb.toString();
    }

    public static String convertStringToSqlString(String ids) {
        String[] idList = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (String id : idList) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(sb.toString())) {
                sb.append(",");
            }
            if (StringUtils.startsWith(id, "'")) {
                id = StringUtils.substring(id, 1, id.length() - 1);
            }
            if (StringUtils.endsWith(id, ",")) {
                id = StringUtils.substring(id, 0, id.length() - 1);
            }
            id = org.apache.commons.lang.StringEscapeUtils.escapeSql(id);
            sb.append("'").append(id).append("'");
        }
        return sb.toString();
    }

    public static boolean isNumberList(String ids) {
        boolean result = true;
        String[] idList = ids.split(",");
        for (String id : idList) {
            if (StringUtils.isNumeric(id)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static String trimString(String s) {
        return trimString(s, true);
    }

    public static String trimString(String s, boolean bReturnNullWhenEmpty) {
        if (s == null) {
            return null;
        } else if (s.isEmpty()) {
            if (bReturnNullWhenEmpty) {
                return null;
            } else {
                return "";
            }
        }
        return s.trim();
    }

    public static String rightPadWithNull(String source, int size, String padStr) {
        if (source == null) {
            return org.apache.commons.lang3.StringUtils.rightPad("", size, padStr);
        }

        return StringUtils.rightPad(source, size, padStr);
    }

    public static String formatNumber(String number) {
        return formatNumber(number, true);
    }

    public static String formatNumber(String number, boolean showDecimalPlace) {
        try {
            return formatNumber(Double.parseDouble(number), showDecimalPlace);
        } catch (Exception e) {
            return formatNumber(0.0, showDecimalPlace);
        }
    }

    public static String formatNumber(double number) {
        return formatNumber(number, true);
    }

    public static String formatNumber(double number, boolean showDecimalPlace) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String ret = formatter.format(number);
        if (!showDecimalPlace && ret.endsWith(".00")) {
            // if NOT show digits after decimal place, remove ".00"
            return ret.substring(0, ret.length() - 3);
        } else {
            // return format with comma and decimal place
            return ret;
        }

    }

    public static String formatCurrency(double number, boolean showDecimalPlace) {
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        String ret = formatter.format(number);
        if (!showDecimalPlace && ret.endsWith(".00")) {
            // if NOT show digits after decimal place, remove ".00"
            return ret.substring(0, ret.length() - 3);
        } else {
            // return format with comma and decimal place
            return ret;
        }

    }

    public static String combineStringByComma(String firstName, String lastName) {
        StringBuffer sb = new StringBuffer();
        if (org.apache.commons.lang.StringUtils.isNotEmpty(firstName)) {
            sb.append(firstName);
        }
        if (org.apache.commons.lang.StringUtils.isNotEmpty(lastName)) {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(sb.toString())) {
                sb.append(", ");
            }
            sb.append(lastName);
        }
        return sb.toString();
    }

    public static Map<String, String> parseResponse(String response, String entrySplit, String keyValueSplit) {
        Map<String, String> responseMap = new HashMap<String, String>();

        if (org.apache.commons.lang3.StringUtils.isBlank(entrySplit)) {
            entrySplit = ";";
        }


        if (org.apache.commons.lang3.StringUtils.isBlank(keyValueSplit)) {
            keyValueSplit = ":";
        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(response)) {
            for (String item : org.apache.commons.lang3.StringUtils.split(response, entrySplit)) {
                int i = 0;
                String key = null;
                String value = null;
                for (String keyValue : org.apache.commons.lang3.StringUtils.split(item, keyValueSplit)) {
                    if (i == 0) {
                        key = keyValue;
                    } else {
                        value = keyValue;
                    }
                    i++;
                }

                if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
                    value = key;
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(key) && org.apache.commons.lang3.StringUtils.isNotBlank(value)) {
                    responseMap.put(key, value);
                }
            }

        }

        return responseMap;
    }

    public static String capitalizeForSentence(String str) {
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)",
                Pattern.CASE_INSENSITIVE).matcher(str);
        while (m.find()) {
            m.appendReplacement(sb,
                    m.group(1).toUpperCase() + m.group(2).toLowerCase());
        }
        return m.appendTail(sb).toString();
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) return "";
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String fileNameFormatter(String name) {
        String orgFileName = "";
        if (StringUtils.isNotBlank(name)) {
            int nameStart = name.lastIndexOf("/") + 1;
            int nameEnd = name.length();
//            if (name.contains("_")) {
//                nameEnd = name.lastIndexOf("_");
//            }
            orgFileName = name.substring(nameStart, nameEnd);
        }
        return orgFileName;
    }
}
