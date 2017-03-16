package com.wjxinfo.core.base.utils.reflection_properties;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.Transient;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Reflection tools
 */
@SuppressWarnings("rawtypes")//去除警告
public class ReflectionUtils {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * Call getter method
     *
     * @param obj
     * @param propertyName
     * @return
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return object;
    }

    /**
     * Get Object properties
     *
     * @param obj
     * @return
     */
    public static List<String> getObjectProperties(Object obj) {
        Validate.notNull(obj, "object can't be null");
        List<String> list = getClassFieldNames(obj.getClass());
        return list;
    }

    public static boolean isExistPropertyInObject(Object obj, String property) {
        Validate.notNull(obj, "object can't be null");
        return isExistPropertyInClass(obj.getClass(), property);
    }

    public static boolean isExistPropertyInClass(Class<?> clazz, String property) {
        Validate.notNull(clazz, "object can't be null");
        boolean result = false;
        List<String> fieldNames = getClassFieldNames(clazz);
        for (String fieldName : fieldNames) {
            if (fieldName.equalsIgnoreCase(property)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static List<String> getClassFieldNames(Class<?> clazz) {
        return getClassFieldNames(clazz, true);
    }

    public static List<String> getClassFieldNames(Class<?> clazz, boolean withTransient) {
        List<String> fieldNames = new ArrayList<String>();
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                if (withTransient) {
                    if (!fieldNames.contains(field.getName())) {
                        fieldNames.add(field.getName());
                    }
                } else {
                    if (!field.isAnnotationPresent(Transient.class) && !fieldNames.contains(field.getName())) {
                        fieldNames.add(field.getName());
                    }
                }
            }
        }
        return fieldNames;
    }

    /**
     * Get Field name and value
     *
     * @param obj
     * @return
     */
    public static String getFieldValue(Object obj, boolean isGetCollectionValue) {
        Validate.notNull(obj, "object can't be null");
        Field[] fields = obj.getClass().getDeclaredFields();
        String result = "";
        for (Field field : fields) {
            Object value = getFieldValue(obj, field.getName());
            if (isGetCollectionValue && value instanceof Collection) {
                value = getCollectionValue((Collection) value);
            }
            if (!isGetCollectionValue && (value instanceof Collection)) {
                value = "";
            }
            result += (result.equals("") ? "\"" : ";\"") + field.getName() + "\":\"" + (value == null ? "\"" : value.toString() + "\"");
        }
        result = "{" + result + "}";
        return result;
    }

    /**
     * Get Collection Object Value
     *
     * @param list
     * @return
     */
    public static String getCollectionValue(Collection list) {
        String result = "";
        for (Object obj : list) {
            result += (result.equals("") ? "" : ",") + getFieldValue(obj, false);
        }
        result = "[" + result + "]";
        return result;
    }

    /**
     * Get different value string for two object
     *
     * @param oldObject
     * @param newObject
     * @param fieldNames
     * @return
     */
    public static List<String> getDiffStringByObject(final Object oldObject, final Object newObject, final List<String> fieldNames) {
        List<String> result = new ArrayList<String>();
        if (fieldNames == null || (fieldNames != null && fieldNames.size() == 0) || (oldObject == null && newObject == null)
                /*|| (oldObject != null && newObject != null && !oldObject.getClass().equals(newObject.getClass()))*/) {
            return result;
        }
        String oldValue = "";
        String newValue = "";
        for (String fieldName : fieldNames) {
            if (oldObject != null && newObject == null) {
                Object fieldValue = getFieldValue(oldObject, fieldName);
                if (fieldValue instanceof Collection) {
                    fieldValue = getCollectionValue((Collection) fieldValue);
                }
                oldValue += (oldValue.equals("") ? "\"" : ";\"") + fieldName + "\":\"" + (fieldValue == null ? "\"" : fieldValue.toString() + "\"");
            } else if (oldObject == null && newObject != null) {
                Object fieldValue = getFieldValue(newObject, fieldName);
                if (fieldValue instanceof Collection) {
                    fieldValue = getCollectionValue((Collection) fieldValue);
                }
                newValue += (newValue.equals("") ? "\"" : ";\"") + fieldName + "\":\"" + (fieldValue == null ? "\"" : fieldValue.toString() + "\"");
            } else if (oldObject != null && newObject != null) {
                Object value1 = getFieldValue(oldObject, fieldName);
                Object value2 = getFieldValue(newObject, fieldName);
                if (value1 instanceof Collection) {
                    value1 = getCollectionValue((Collection) value1);
                }
                if (value2 instanceof Collection) {
                    value2 = getCollectionValue((Collection) value2);
                }
                if ((value1 == null && value2 != null)
                        || (value1 != null && value2 == null)
                        || (value1 != null && value2 != null && !value1.toString().equals(value2.toString()))) {
                    oldValue += (oldValue.equals("") ? "\"" : ";\"") + fieldName + "\":\"" + (value1 == null ? "\"" : value1.toString() + "\"");
                    newValue += (newValue.equals("") ? "\"" : ";\"") + fieldName + "\":\"" + (value2 == null ? "\"" : value2.toString() + "\"");
                }
            }
        }
        if (StringUtils.isNotEmpty(oldValue)) {
            oldValue = "{" + oldValue + "}";
        }
        if (StringUtils.isNotEmpty(newValue)) {
            newValue = "{" + newValue + "}";
        }
        if (StringUtils.isNotEmpty(oldValue) || StringUtils.isNotEmpty(newValue)) {
            result.add(oldValue);
            result.add(newValue);
        }
        return result;
    }

    /**
     * call setter method
     *
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }

    /**
     * get object value
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            logger.error("Could not find field [" + fieldName + "] on target [" + obj + "]");
            //throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }
        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("exception{}", e.getMessage());
        }
        return result;
    }

    /**
     * get object value
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("exception:{}", e.getMessage());
        }
    }

    /**
     * get object value
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param args
     * @return
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            logger.error("Could not find method [" + methodName + "] on target [" + obj + "]");
            //throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("Method run error: ", e.getMessage());
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * get object value
     *
     * @param obj
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    public static Field getAccessibleField(final Class<?> clazz, final String fieldName) {
        Validate.notNull(clazz, "class can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                continue;// new add
            }
        }
        return null;
    }

    /**
     * get object declaredField, set access.
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {//NOSONAR
                continue;// new add
            }
        }
        return null;
    }

    /**
     * get object declaredField, set access.
     *
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                continue;// new add
            }
        }
        return null;
    }

    /**
     * get object declaredField, set access.
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * change private/protected  to public
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * change private/protected  to public
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
                .isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * get class type
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * get class type
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class getClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * get class super class
     *
     * @param instance
     * @return
     */
    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * change checked exception to unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * @param orig         - Original / Source Object
     * @param dest         - Destination Object
     * @param fieldMapping
     * @return Error Message List during copy properties
     */
    public static List<String> copyProperties(Object orig, Object dest, String[] fieldMapping) {
//        List<String> list = new ArrayList<String>();
//        if (orig == null || dest == null) {
//            return list;
//        }
//
//        if (fieldMapping != null && fieldMapping.length > 0) {
//            for (int i = 0; i < fieldMapping.length; i++) {
//                String mappingStr = fieldMapping[i];
//                if (StringUtils.isNotBlank(mappingStr)) {
//                    String[] mappingArr = mappingStr.split(":");
//
//                    String origFieldName = mappingArr[0];
//                    String destFieldName = null;
//                    if (mappingArr.length > 1) {
//                        destFieldName = mappingArr[1];
//                    } else {
//                        destFieldName = origFieldName;
//                    }
//
//                    String err = copyProperty(orig, dest, origFieldName, destFieldName);
//                    if(StringUtils.isNotBlank(err)){
//                        list.add(err);
//                    }
//                }
//            }
//        }
//
//        return list;

        // 2015-12-14
        return copyPropertiesWithEnum(orig, dest, fieldMapping);
    }

    /**
     * @param orig          - Original/Source Object
     * @param dest          - Destination Object
     * @param origFieldName Original Field Name
     * @param destFieldName Destination Field Name
     * @return Error Message during copy property
     */
    public static String copyProperty(Object orig, Object dest, String origFieldName, String destFieldName) {
        // 2015-12-14
        return copyPropertyWithEnum(orig, dest, origFieldName, destFieldName);
        /*
        String err = null;
        try {
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
//            DateConverter dateConverter = new DateConverter();
//            dateConverter.setPattern("dd-MM-yyyy");
//            BeanUtilsBean2.getInstance().getConvertUtils().register(dateConverter,java.util.Date.class);
            Object origValue = BeanUtilsBean2.getInstance().getPropertyUtils().getNestedProperty(orig, origFieldName);

            // Just check the field name is exist or not
            BeanUtilsBean2.getInstance().getPropertyUtils().getSimpleProperty(dest, destFieldName);

            BeanUtilsBean2.getInstance().copyProperty(dest, destFieldName, origValue);
        } catch (IllegalAccessException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        }

        return err;
        */
    }

    public static List<String> copyPropertiesWithEnum(Object orig, Object dest, String[] fieldMapping) {
        List<String> list = new ArrayList<String>();
        if (orig == null || dest == null) {
            return list;
        }

        if (fieldMapping != null && fieldMapping.length > 0) {
            for (int i = 0; i < fieldMapping.length; i++) {
                String mappingStr = fieldMapping[i];
                if (StringUtils.isNotBlank(mappingStr)) {
                    String[] mappingArr = mappingStr.split(":");

                    String origFieldName = mappingArr[0];
                    String destFieldName = null;
                    if (mappingArr.length > 1) {
                        destFieldName = mappingArr[1];
                    } else {
                        destFieldName = origFieldName;
                    }

                    String err = copyPropertyWithEnum(orig, dest, origFieldName, destFieldName);
                    if (StringUtils.isNotBlank(err)) {
                        list.add(err);
                    }
                }
            }
        }

        return list;
    }


    public static String copyPropertyWithEnum(Object orig, Object dest, String origFieldName, String destFieldName) {
        String err = null;
        try {
            BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));

            BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
            beanUtilsBean.getConvertUtils().register(false, false, 0);

//            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
//            DateConverter dateConverter = new DateConverter();
//            dateConverter.setPattern("dd-MM-yyyy");
//            BeanUtilsBean2.getInstance().getConvertUtils().register(dateConverter,java.util.Date.class);
            Object origValue = beanUtilsBean.getPropertyUtils().getNestedProperty(orig, origFieldName);

            // Just check the field name is exist or not
            beanUtilsBean.getPropertyUtils().getSimpleProperty(dest, destFieldName);

            beanUtilsBean.copyProperty(dest, destFieldName, origValue);
        } catch (IllegalAccessException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            err = "Copy Property Error: " + e.getMessage() + " Original Field =" + origFieldName + ", Dest Field = " + destFieldName;
            e.printStackTrace();
        }

        return err;
    }

    /**
     * @param objects
     * @param clazz
     * @param filedMapping
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static <T> List<T> convertSqlResult(List<Map<String, Object>> objects, Class<T> clazz, Map<String, String> filedMapping)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> pojos = new ArrayList<T>();

        for (Map<String, Object> objectMap : objects) {
            T pojo = clazz.newInstance();

            for (Map.Entry<String, Object> property : objectMap.entrySet()) {
                String pojoFieldName = filedMapping.get(property.getKey());
                // System.out.println(property.getKey() + " -> " + pojoFieldName);

                if (StringUtils.isNotBlank(pojoFieldName)) {

                    //Method setter = clazz.getMethod("set" + property.getKey().substring(0, 1).toUpperCase()
                    //        + property.getKey().substring(1), property.getValue().getClass());

                    Method setter = clazz.getMethod("set" + pojoFieldName.substring(0, 1).toUpperCase()
                            + pojoFieldName.substring(1), property.getValue().getClass());

                    setter.invoke(pojo, property.getValue());

                }
            }
            pojos.add(pojo);
        }
        return pojos;
    }

    static class EnumAwareConvertUtilsBean extends ConvertUtilsBean {
        private final EnumConverter enumConverter = new EnumConverter();

        public Converter lookup(Class clazz) {
            final Converter converter = super.lookup(clazz);
            // no specific converter for this class, so it's neither a String, (which has a default converter),
            // nor any known object that has a custom converter for it. It might be an enum !
            if (converter == null && clazz.isEnum()) {
                return enumConverter;
            } else {
                return converter;
            }
        }

        private class EnumConverter implements Converter {
            public Object convert(Class type, Object value) {
                return Enum.valueOf(type, (String) value);
            }
        }
    }
}
