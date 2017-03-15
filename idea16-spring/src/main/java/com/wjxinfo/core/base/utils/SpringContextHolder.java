package com.wjxinfo.core.base.utils;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

//@Service
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    private static void assertContextInjected() {
        Validate.validState(applicationContext != null, "applicationContext have not autowared, please define SpringContextHolder in applicationContext.xml.");
    }

    public static void registerSingleton(String beanName, Object bean) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(beanName) && bean != null) {
            if (applicationContext != null && applicationContext instanceof ConfigurableApplicationContext) {
                logger.info("Register bean: " + beanName);
                ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();

                beanFactory.registerSingleton(beanName, bean);
            }
        }

    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
}