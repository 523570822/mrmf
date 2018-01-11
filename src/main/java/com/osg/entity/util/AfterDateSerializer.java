package com.osg.entity.util;

import com.osg.framework.util.DateUtil;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * Created by 蔺哲 on 2017/7/26.
 */
public class AfterDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String formattedDate = DateUtil.getAfterDay(value);
        jgen.writeString(formattedDate);
    }
}
