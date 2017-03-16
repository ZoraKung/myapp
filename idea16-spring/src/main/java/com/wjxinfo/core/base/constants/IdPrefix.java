package com.wjxinfo.core.base.constants;

/**
 * Created by WTH on 2015/10/29.
 */
public final class IdPrefix {
    public final static String[] ID_PREFIX = new String[]{
            "t_ind_mem_info:IN"
    };

    public final static String[] ID_FORMAT = new String[]{
            "t_ind_mem_info:IN%1$07d"
    };

    public static String getPrefix(String tableName) {
        for (String tableAndPrefix : ID_PREFIX) {
            String[] arr = tableAndPrefix.split(":");
            if (arr != null && arr.length > 1 && arr[0] != null && arr[0].equalsIgnoreCase(tableName)) {
                return arr[1];
            }
        }

        return "";
    }

    public static String getFormat(String tableName) {
        for (String tableAndPrefix : ID_FORMAT) {
            String[] arr = tableAndPrefix.split(":");
            if (arr != null && arr.length > 1 && arr[0] != null && arr[0].equalsIgnoreCase(tableName)) {
                return arr[1];
            }
        }

        return "";
    }
}
