package com.osg.framework.mongodb;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class UtilDateToSqlTime implements Converter<Date, Timestamp>{

    public Timestamp convert(Date source) {
        if(source != null) {
            return new Timestamp(source.getTime());
        }
        return null;
    }

}
