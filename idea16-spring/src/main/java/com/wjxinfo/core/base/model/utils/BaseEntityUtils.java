package com.wjxinfo.core.base.model.utils;

import com.wjxinfo.core.base.model.BaseEntity;
import com.wjxinfo.core.base.utils.common.DateUtils;
import com.wjxinfo.core.base.utils.reflection_properties.ReflectionUtils;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.persistence.HibernateUtils;
import com.wjxinfo.core.base.web.utils.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Author: Jack
 * Date: 15-1-6
 * Description: Base Entity Utils
 */
public class BaseEntityUtils {
    private static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    public static String OLD_VALUE = "old";

    public static String NEW_VALUE = "new";

    private static EntityManagerFactory emf = SpringContextHolder.getBean(EntityManagerFactory.class);

    //get class install by class name and instance id
    public static BaseEntity getInstanceByClassAndId(Class<?> clazz, String objectId) {
        BaseEntity baseEntity = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            baseEntity = (BaseEntity) em.find(clazz, objectId);
        } finally {
            em.close();
        }
        return baseEntity;
    }

    public static String getValueByObject(Object value) {
        if (value == null) {
            return "";
        }
        String result = value.toString();
        if (value instanceof Date) {
            result = DateUtils.format((Date) value);
        }
        return result;
    }

//    public static void throwDifferenceException(Class<?> clazz, String objectId, Object newObject) {
//        String differences = getDifferenceStringBetweenTwoObject(clazz, objectId, newObject);
//        logger.info("two object difference = " + differences);
//        if (StringUtils.isNotEmpty(differences)) {
//            throw new Exception(differences);
//        }
//    }

    public static String getDifferenceStringBetweenTwoObject(Class<?> clazz, String objectId, Object oldObject) {
        StringBuffer result = new StringBuffer();
        Object newObject = getInstanceByClassAndId(clazz, objectId);
        Map<String, Map<String, String>> differences = compareProperties(oldObject, newObject);
        for (String key : differences.keySet()) {
            if (StringUtils.isNotEmpty(result.toString())) {
                result.append("\n");
            }
            result.append(I18nUtils.getMessage("object.property.change", new String[]{key, differences.get(key).get(OLD_VALUE), differences.get(key).get(NEW_VALUE)}));
        }
        if (StringUtils.isNotEmpty(result.toString())) {
            result.append(I18nUtils.getMessage("reload.data"));
        }
        return result.toString();
    }

    public static Map<String, Map<String, String>> compareProperties(Object oldObject, Object newObject) {
        return compareProperties(oldObject, newObject, "id,createDate,createBy,lastUpdatedDate,lastUpdatedBy,deleteFlag");
    }

    public static Map<String, Map<String, String>> compareProperties(Object oldObject, Object newObject, String excludeProperties) {
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        Class<?> clazz = oldObject.getClass();
        List<String> properties = ReflectionUtils.getClassFieldNames(clazz, false);
        for (String property : properties) {
            if (!HibernateUtils.isLazyProperty(clazz, property) && !excludeProperties.contains(property)) {
                Object oldValue = ReflectionUtils.getFieldValue(oldObject, property);
                Object newValue = ReflectionUtils.getFieldValue(newObject, property);
                String oldString = getValueByObject(oldValue);
                String newString = getValueByObject(newValue);
                if (!oldString.equalsIgnoreCase(newString)) {
                    Map<String, String> values = new HashMap<String, String>();
                    values.put(OLD_VALUE, oldString);
                    values.put(NEW_VALUE, newString);
                    result.put(property, values);
                }
            }
        }
        return result;
    }

    public static BaseEntity setEntityPropertyToNull(BaseEntity newObject) {
        for (Class<?> superClass = newObject.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                Object fieldValue = ReflectionUtils.getFieldValue(newObject, field.getName());
                if (fieldValue != null && !field.isAnnotationPresent(Transient.class)
                        && !field.getName().equalsIgnoreCase("serialVersionUID")
                        && ReflectionUtils.isExistPropertyInObject(newObject, field.getName())
                        ) {
                    ReflectionUtils.invokeSetter(newObject, field.getName(), null);
                }
            }
        }
        return newObject;
    }

    public static BaseEntity setEntityProperty(BaseEntity newObject, BaseEntity oldObject) {
        for (Class<?> superClass = newObject.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                Object fieldValue = ReflectionUtils.getFieldValue(newObject, field.getName());
                if (fieldValue != null && !field.isAnnotationPresent(Transient.class)
                        && !field.getName().equalsIgnoreCase("serialVersionUID")
                        && !field.getName().equalsIgnoreCase("id")
                        && ReflectionUtils.isExistPropertyInObject(newObject, field.getName())
                        ) {
                    if (field.getName().equalsIgnoreCase(oldObject.getClass().getSimpleName())) {
                        ReflectionUtils.invokeSetter(newObject, field.getName(), oldObject);
                    } else {
                        if (fieldValue instanceof BaseEntity && field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)
                                ) {
                            BaseEntity oneObject = (BaseEntity) fieldValue;
                            if (ReflectionUtils.isExistPropertyInObject(oneObject, newObject.getClass().getSimpleName())) {
                                ReflectionUtils.invokeSetter(oneObject, newObject.getClass().getSimpleName().replace("PC", "pc"), newObject);
                            }
                            ReflectionUtils.invokeSetter(newObject, field.getName(), oneObject);
                        } else if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
                            ReflectionUtils.invokeSetter(newObject, field.getName(), null);
                        } else {
                            ReflectionUtils.invokeSetter(newObject, field.getName(), fieldValue);
                        }
                    }
                }
            }
        }
        return newObject;
    }

    public static BaseEntity singleClone(BaseEntity oldObject, String... excludeProperties) {
        BaseEntity newObject = oldObject.clone();
        for (Class<?> superClass = oldObject.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                //get field value
                Object fieldValue = ReflectionUtils.getFieldValue(oldObject, field.getName());
                if (fieldValue != null && !field.isAnnotationPresent(Transient.class)
                        && !field.getName().equalsIgnoreCase("serialVersionUID")
                        && ReflectionUtils.isExistPropertyInObject(oldObject, field.getName())
                        ) {
                    if (excludeProperties != null && Arrays.asList(excludeProperties).contains(field.getName())) {
                        ReflectionUtils.invokeSetter(newObject, field.getName(), null);
                    } else {
                        if (fieldValue instanceof BaseEntity && field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
                            //process ManyToOne, OneToOne
                            BaseEntity oneObject = ((BaseEntity) fieldValue).clone();
                            if (ReflectionUtils.isExistPropertyInObject(oneObject, newObject.getClass().getSimpleName())) {
                                ReflectionUtils.invokeSetter(oneObject, newObject.getClass().getSimpleName().replace("PC", "pc"), newObject);
                            }
                            ReflectionUtils.invokeSetter(newObject, field.getName(), oneObject);
                        } else if ((fieldValue instanceof List || fieldValue instanceof Set) && field.isAnnotationPresent(ManyToMany.class) || field.isAnnotationPresent(OneToMany.class)) {
                            //process OneToMany, ManyToMany
                            if (fieldValue instanceof List) {
                                List list = new ArrayList();
                                for (Object obj : (List) fieldValue) {
                                    if (obj instanceof BaseEntity) {
                                        BaseEntity newObj = ((BaseEntity) obj).clone();
                                        setEntityProperty(newObj, newObject);
                                        list.add(newObj);
                                    }
                                }
                                ReflectionUtils.invokeSetter(newObject, field.getName(), list);
                            } else if (fieldValue instanceof Set) {
                                Set list = new HashSet();
                                for (Object obj : (Set) fieldValue) {
                                    if (obj instanceof BaseEntity) {
                                        BaseEntity newObj = ((BaseEntity) obj).clone();
                                        setEntityProperty(newObj, newObject);
                                        list.add(newObj);
                                    }
                                }
                                ReflectionUtils.invokeSetter(newObject, field.getName(), list);
                            }
                        } else {
                            //process other property
                            ReflectionUtils.invokeSetter(newObject, field.getName(), ReflectionUtils.getFieldValue(oldObject, field.getName()));
                        }
                    }
                }
            }
        }
        return newObject;
    }
}
