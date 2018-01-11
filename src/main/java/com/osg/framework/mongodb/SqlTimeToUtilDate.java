package com.osg.framework.mongodb;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class SqlTimeToUtilDate implements Converter<Timestamp, Date> {

    public Date convert(Timestamp source) {
        if (source != null) {
            return new Date(source.getTime());
        }
        return null;
    }

}
