package com.wjxinfo.core.base.utils.common;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Collection Utils
 */
public final class CollectionHelper {

    /**
     * Get property of collection to list
     *
     * @param collection   : Collection
     * @param propertyName : Property name
     * @return : List
     */
    public static List extractToList(final Collection collection, final String propertyName) {
        List list = new ArrayList(collection.size());

        try {
            for (Object obj : collection) {
                if (!list.contains(PropertyUtils.getProperty(obj, propertyName))) {
                    list.add(PropertyUtils.getProperty(obj, propertyName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //new MessagingException("Extract to list error: ", e);
        }

        return list;
    }

    /**
     * Convert property of collection data to string
     *
     * @param collection   : Collection
     * @param propertyName : Property name
     * @param separator    : Separator
     * @return : String
     */
    public static String extractToString(final Collection collection, final String propertyName, final String separator) {
        List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * Convert collection all data to string
     *
     * @param collection : Collection
     * @param separator  : Separator
     * @return : String
     */
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * Collection is empty
     *
     * @param collection : Collection
     * @return : True / False
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Collection is not empty
     *
     * @param collection : Collection
     * @return : True / False
     */
    public static boolean isNotEmpty(Collection collection) {
        return (collection != null && !(collection.isEmpty()));
    }

    /**
     * @param values
     * @return
     */
    public static List<String> stringToList(String values) {
        String[] arrValue = values.split(",");
        return Arrays.asList(arrValue);
    }

    /**
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T getFirstItem(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        } else {
            return collection.iterator().next();
        }
    }
}
