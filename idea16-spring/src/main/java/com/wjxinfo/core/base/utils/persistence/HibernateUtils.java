package com.wjxinfo.core.base.utils.persistence;

import com.wjxinfo.core.base.utils.reflection_properties.ReflectionUtils;
import com.wjxinfo.core.base.utils.common.SpringContextHolder;
import com.wjxinfo.core.base.vo.LabelValue;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Hibernate Lazy Utils
 * Created by Jack on 14-5-30.
 */
public class HibernateUtils {

    private static EntityManagerFactory emf = SpringContextHolder.getBean(EntityManagerFactory.class);

    private static Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

    /**
     * Initialize Entity Lazy property
     *
     * @param obj: e.g. -- user.getRoles()
     */
    public static void initializeProperty(Object obj) {
        Hibernate.initialize(obj);
    }

    /**
     * Entity class property , fetch ? = lazy
     *
     * @param entityClass
     * @param propertyName
     * @return
     */
    public static boolean isLazyProperty(Class<?> entityClass, String propertyName) {
        boolean isLazy = false;
        try {
            Field field = ReflectionUtils.getAccessibleField(entityClass, propertyName);
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany oneToMany = field.getAnnotation(OneToMany.class);
                if (oneToMany.fetch() != null && oneToMany.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
                if (manyToMany.fetch() != null && manyToMany.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                if (manyToOne.fetch() != null && manyToOne.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(OneToOne.class)) {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                if (oneToOne.fetch() != null && oneToOne.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            }

        } catch (Exception ex) {
            logger.error(String.format("if field is lazy error: %s", ex.getMessage()));
        }
        return isLazy;
    }

    /**
     * Is Json Ignore
     *
     * @param entityClass
     * @param propertyName
     * @return
     */
    public static boolean isJsonIgnoreProperty(Class<?> entityClass, String propertyName) {
        boolean isJsonIgnore = false;
        try {
            Field field = entityClass.getDeclaredField(propertyName);
            if (field.isAnnotationPresent(JsonIgnore.class)) {
                isJsonIgnore = true;
            }

        } catch (Exception ex) {
            logger.error(String.format("if field is json ignore error: %s", ex.getMessage()));
        }
        return isJsonIgnore;
    }

    /**
     * Is Lazy And Json Ignore Property
     *
     * @param entityClass
     * @param propertyName
     * @return
     */
    public static boolean isLazyAndJsonIgnoreProperty(Class<?> entityClass, String propertyName) {
        boolean isLazy = false;
        boolean isJsonIgnore = false;
        try {
            Field field = entityClass.getDeclaredField(propertyName);
            if (field.isAnnotationPresent(JsonIgnore.class)) {
                isJsonIgnore = true;
            }
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany oneToMany = field.getAnnotation(OneToMany.class);
                if (oneToMany.fetch() != null && oneToMany.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(ManyToMany.class)) {
                ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
                if (manyToMany.fetch() != null && manyToMany.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                if (manyToOne.fetch() != null && manyToOne.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            } else if (field.isAnnotationPresent(OneToOne.class)) {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                if (oneToOne.fetch() != null && oneToOne.fetch().compareTo(FetchType.LAZY) == 0) {
                    isLazy = true;
                }
            }

        } catch (Exception ex) {
            logger.error(String.format("if field is lazy and json ignore error: %s", ex.getMessage()));
        }
        return isLazy && (!isJsonIgnore);
    }

    /**
     * Entity instance property, fetch ? = lazy
     *
     * @param object
     * @param propertyName
     * @return
     */
    public static boolean isLazyProperty(Object object, String propertyName) {
        Class<?> entityClass = object.getClass();
        return isLazyProperty(entityClass, propertyName);
    }

    /**
     * Get All Lazy Fields
     *
     * @param entityClass
     * @return
     */
    public static List<Field> getLazyFields(Class<?> entityClass) {
        List<Field> fieldList = new ArrayList<Field>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (isLazyProperty(entityClass, field.getName())) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    /**
     * Get Object All lazy fields
     *
     * @param object
     * @return
     */
    public static List<Field> getLazyFields(Object object) {
        return getLazyFields(object.getClass());
    }

    /**
     * Initialize Object All Lazy
     *
     * @param object
     */
    public static void initializeObjectLazy(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isLazyProperty(object.getClass(), field.getName())) {
                try {
                    initializeProperty(ReflectionUtils.invokeGetter(object, field.getName()));
                } catch (Exception ex) {
                    logger.error(String.format("Initialize object %s property %s error: %s", object.toString(), field.getName(), ex.getMessage()));
                }
            }
        }
    }

    public static void initializeObjectLazyAndJsonIgnore(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isLazyAndJsonIgnoreProperty(object.getClass(), field.getName())) {
                try {
                    initializeProperty(ReflectionUtils.invokeGetter(object, field.getName()));
                } catch (Exception ex) {
                    logger.error(String.format("Initialize object %s property %s error: %s", object.toString(), field.getName(), ex.getMessage()));
                }
            }
        }
    }

    public static void initializeObjectLazy(Object object, List<String> properties) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isLazyProperty(object.getClass(), field.getName()) && (properties.contains(field.getName()))) {
                try {
                    initializeProperty(ReflectionUtils.invokeGetter(object, field.getName()));
                } catch (Exception ex) {
                    logger.error(String.format("Initialize object %s property %s error: %s", object.toString(), field.getName(), ex.getMessage()));
                }
            }
        }
    }

    public static <T> T findEager(EntityManager em, Class<T> type, Object id) {
        T entity = em.find(type, id);
        for (Field field : type.getDeclaredFields()) {
            if (isLazyProperty(type, field.getName()) && Collection.class.isAssignableFrom(field.getType())) {
                try {
                    new PropertyDescriptor(field.getName(), type).getReadMethod().invoke(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return entity;
    }

    public static <T> T getEntityWithLazyProperty(EntityManager em, Class<T> type, Object entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(type);
        Root entity = query.from(type);
        for (Field field : type.getDeclaredFields()) {
            if (isLazyProperty(type, field.getName())) {
                entity.fetch(field.getName());
            }
        }
        Predicate condition = cb.equal(entity.get("id"), entityId);
        query.where(condition);
        return (T) em.createQuery(query.select(entity)).getSingleResult();
    }

    public static List<LabelValue> getAllEntityName() {
        List<LabelValue> list = new ArrayList<LabelValue>();
        for (EntityType<?> entity : emf.getMetamodel().getEntities()) {
            LabelValue labelValue = new LabelValue();
            labelValue.setLabel(entity.getJavaType().getSimpleName());
            labelValue.setValue(entity.getJavaType().getName());
            list.add(labelValue);
        }
        return list;
    }

    public static List<LabelValue> getEntityName(String entityName) {
        logger.info("entityName=" + entityName);
        List<LabelValue> list = new ArrayList<LabelValue>();
        try {
            Field[] fields = Class.forName(entityName).getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ManyToOne.class)) {
                    LabelValue labelValue = new LabelValue();
                    labelValue.setLabel(field.getName());
                    labelValue.setValue(field.getName());
                    list.add(labelValue);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Get class type from class name error: %s", entityName));
        }
        return list;
    }

    public static String getTableNameByClass(Class<?> entityClass) {
        String _tableName = "";
        if (entityClass.isAnnotationPresent(Entity.class) && entityClass.isAnnotationPresent(Table.class)) {
            _tableName = entityClass.getAnnotation(Table.class).name();
        }
        return _tableName;
    }

    public static String getColumnNameByField(Field field) {
        String _columnName = "";
        if (!field.isAnnotationPresent(Transient.class)
                && !field.getName().equalsIgnoreCase("serialVersionUID")
                && !field.isAnnotationPresent(JsonIgnore.class)
                ) {
            if (field.isAnnotationPresent(Column.class)) {
                _columnName = field.getAnnotation(Column.class).name();
            } else if (field.isAnnotationPresent(JoinColumn.class)) {
                _columnName = field.getAnnotation(JoinColumn.class).name();
            } else {
                _columnName = getDefaultColumnName(field.getName());
            }
        }
        return _columnName;
    }

    public static String getColumnNameByClassAndFieldName(Class<?> entityClass, String fieldName) {
        String _columnName = "";
        for (Class<?> superClass = entityClass; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                if (field != null) {
                    _columnName = getColumnNameByField(field);
                    break;
                }
            } catch (NoSuchFieldException e) {
                //logger.error(String.format("Get field from class error: %s", e.getMessage()));
                continue;
            }
        }
        if (StringUtils.isNotEmpty(_columnName)) {
            if (entityClass.isAnnotationPresent(AttributeOverride.class)) {
                if (entityClass.getAnnotation(AttributeOverride.class).name().equalsIgnoreCase(_columnName)) {
                    _columnName = entityClass.getAnnotation(AttributeOverride.class).column().name();
                }
            }
        }
        return _columnName;
    }

    public static String getDefaultColumnName(String fieldName) {
        StringBuffer sb = new StringBuffer();
        char[] arr = fieldName.toCharArray();
        for (char ch : arr) {
            if (Character.isUpperCase(ch)) {
                sb.append("_").append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
