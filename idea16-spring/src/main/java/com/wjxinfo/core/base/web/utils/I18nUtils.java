package com.wjxinfo.core.base.web.utils;

import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class I18nUtils {
    public static final String LANGUAGE = "language";
    private static final Logger logger = LoggerFactory.getLogger(I18nUtils.class);
    public static Locale defaultLocal = new Locale("zh");

    private static MessageSource messageSource = SpringContextHolder.getBean("messageSource");

    private static LocaleResolver localeResolver = SpringContextHolder.getBean("localeResolver");

    public static void setDefaultLocal(HttpServletRequest request) {
        if (localeResolver != null) {
            defaultLocal = localeResolver.resolveLocale(request);
        }
    }

    public static String getLocaleLanguage() {
        if (defaultLocal == null) {
            return "zh";
        } else {
            return defaultLocal.getLanguage();
        }
    }

    public static String getMessage(String code) {
        try {
            return messageSource.getMessage(code, null, defaultLocal);
        } catch (Exception e) {
            logger.error(String.format("Cannot found s% in I18N properties.", code));
            //e.printStackTrace();
        }
        return "{" + code + "}";
    }

    public static String getMessage(String code, Object[] args) {
        try {
            return messageSource.getMessage(code, args, defaultLocal);
        } catch (Exception e) {
            logger.error("I18N ERROR - " + e.getMessage() + " code = [" + code + "].");
            // e.printStackTrace();
        }
        return "{" + code + "}" + ",{" + args + "}";
    }
}
