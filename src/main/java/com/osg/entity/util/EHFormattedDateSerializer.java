package com.osg.entity.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @JsonSerialize(using = EHDateSerializer.class) public Date getCreateTime() {
 *                      return createTime; }
 */
public class EHFormattedDateSerializer extends JsonSerializer<Date> {
	// "今天 12:09" "1月1日" "2015年12月12日"
	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		if (value != null) {
			jgen.writeString(convertTimeToFormat(value.getTime()));
		}
	}

	public static String convertTimeToFormat(long time) {
		long curTime = System.currentTimeMillis();
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(curTime);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(time);

		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
			if (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
					&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
				return "今天 " + calendar2.get(Calendar.HOUR_OF_DAY) + ":" + calendar2.get(Calendar.MINUTE);
			} else {
				return (calendar2.get(Calendar.MONTH) + 1) + "月" + calendar2.get(Calendar.DAY_OF_MONTH) + "日";
			}
		} else {
			return calendar2.get(Calendar.YEAR) + "年" + (calendar2.get(Calendar.MONTH) + 1) + "月"
					+ calendar2.get(Calendar.DAY_OF_MONTH) + "日";
		}
	}

}
