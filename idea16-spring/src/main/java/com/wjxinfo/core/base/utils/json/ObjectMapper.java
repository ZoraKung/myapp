package com.wjxinfo.core.base.utils.json;


import com.wjxinfo.common.jqgrid.vo.RequestObject;
import com.wjxinfo.core.base.constants.Constants;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ObjectMapper {
    private static Logger logger = LoggerFactory.getLogger(ObjectMapper.class);

    /**
     * Get Object From Json String
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromJson(String json, Class<T> clazz) {
        try {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
            SimpleModule dateSimpleModule = new SimpleModule("DateDeserializerModule", new Version(1, 0, 0, null));
            dateSimpleModule.addDeserializer(Date.class, new DateJsonDeserializer());
            mapper.registerModule(dateSimpleModule);
            SimpleModule integerSimpleModule = new SimpleModule("IntegerDeserializerModule", new Version(1, 0, 0, null));
            integerSimpleModule.addDeserializer(Integer.class, new IntegerJsonDeserializer());
            mapper.registerModule(integerSimpleModule);
            //Ignore not visible field
            mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return clazz.equals(String.class) ? (T) json : mapper.readValue(json, clazz);
        } catch (Exception ex) {
            logger.error(String.format("From json string to object error: %s", ex.getMessage()));
        }
        return null;
    }

    public static <T> List<T> getObjectListFromJson(String json, Class<T> clazz) {
        try {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat(Constants.DATE_FORMAT));
            return (List<T>) (clazz.equals(String.class) ? (new ArrayList<String>()).add(json) : mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
                    List.class, clazz)));

        } catch (Exception ex) {
            logger.error(String.format("From json string to object error: %s", ex.getMessage()));
        }
        return null;
    }

    public static Map<String, Object> map(String jsonString) {

        if (jsonString != null) {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
            try {
                return mapper.readValue(jsonString, Map.class);
            } catch (Exception e) {
                logger.error(String.format("From json string to object error: %s", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static Map<String, String> convertJsonToMap(String jsonString) {

        if (jsonString != null) {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
            try {
                return mapper.readValue(jsonString, Map.class);
            } catch (Exception e) {
                logger.error(String.format("From json string to object error: %s", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static RequestObject requestMap(String jsonString) {

        if (jsonString != null) {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
            try {
                return mapper.readValue(jsonString, RequestObject.class);
            } catch (Exception e) {
                logger.error(String.format("From json string to object error: &s", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static ArrayList<Map<String, String>> fieldsMap(String jsonString) {

        if (jsonString != null) {
            org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
            try {
                return mapper.readValue(jsonString, ArrayList.class);
            } catch (Exception e) {
                logger.error(String.format("From json string to object error: %s", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static String writeJsonString(Object object) {
        if (object != null) {
            try {
                org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
                SerializationConfig serConfig = mapper.getSerializationConfig();
                mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
                mapper.setSerializerFactory(getJsonSerializerFactory());
                mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
                return mapper.writeValueAsString(object);
            } catch (Exception e) {
                logger.error(String.format("From object to json string error: %s", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static CustomSerializerFactory getJsonSerializerFactory() {
        CustomSerializerFactory serializerFactory = new CustomSerializerFactory();
        serializerFactory.addSpecificMapping(java.sql.Date.class, new JsonSerializer<java.sql.Date>() {
            @Override
            public void serialize(java.sql.Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(new SimpleDateFormat("dd-MM-yyyy").format(date));
            }
        });
        return serializerFactory;
    }
}