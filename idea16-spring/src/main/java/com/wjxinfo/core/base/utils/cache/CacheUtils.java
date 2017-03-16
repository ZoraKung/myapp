package com.wjxinfo.core.base.utils.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Cache Utils
 */
@Service
public class CacheUtils implements ApplicationContextAware {

    public static final String SYS_CACHE = "sysCache";

    private static CacheManager customEhcacheManager;

    public static Object get(String key) {
        return get(SYS_CACHE, key);
    }

    public static void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }

    public static void remove(String key) {
        remove(SYS_CACHE, key);
    }

    public static Object get(String cacheName, String key) {
        if (null != customEhcacheManager) {
            Element element = customEhcacheManager.getCache(cacheName).get(key);
            return element == null ? null : element.getObjectValue();
        }
        return null;
    }

    public static void put(String cacheName, String key, Object value) {
        if (null != customEhcacheManager) {
            Element element = new Element(key, value);
            customEhcacheManager.getCache(cacheName).put(element);
        }
    }

    public static void remove(String cacheName, String key) {
        if (null != customEhcacheManager) {
            customEhcacheManager.getCache(cacheName).remove(key);
        }
    }

    public static void removeAll() {
        if (null != customEhcacheManager) {
            customEhcacheManager.getCache(SYS_CACHE).removeAll();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        customEhcacheManager = (CacheManager) applicationContext.getBean("customEhcacheManager");
    }
}
