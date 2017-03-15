package com.wjxinfo.core.base.utils.bean;

import org.dozer.DozerBeanMapper;

import java.util.*;

/**
 * Created by MrChen on 16-1-13.
 */
public class BeanMapper {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    public static <K, V> Map<K, V> mapMap(Map map, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> destinationMap = new HashMap<K, V>();
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                K destinationKey = null;
                if (key instanceof Object) {
                    destinationKey = (K) key;
                } else {
                    destinationKey = dozer.map(key, keyClass);
                }
                V destinationValue = null;
                if (map.get(key) != null) {
                    if (map.get(key) instanceof Object) {
                        destinationValue = (V) map.get(key);
                    } else {
                        destinationValue = dozer.map(map.get(key), valueClass);
                    }
                }
                destinationMap.put(destinationKey, destinationValue);
            }
        }
        return destinationMap;
    }

    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }
}
