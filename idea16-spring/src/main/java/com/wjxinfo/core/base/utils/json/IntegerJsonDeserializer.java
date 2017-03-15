package com.wjxinfo.core.base.utils.json;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Jack on 15-4-8.
 */
public class IntegerJsonDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (StringUtils.isNotEmpty(jsonParser.getText())) {
            return Integer.parseInt(jsonParser.getText());
        } else {
            return null;
        }
    }
}
