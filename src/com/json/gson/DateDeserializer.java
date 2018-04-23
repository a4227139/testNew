
package com.json.gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date>{

	/**
     * 允许转换的日期格式
     */
    private static String[] parsePatterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM",

    "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日", "yyyy年MM月" };

    /**
     * 毫秒正则表达式
     */
    private static Pattern pattern = Pattern.compile("^[0-9]*$");

    /**
     * date字符串转Date对象处理
     * 
     * @param paramJsonElement
     * @param paramType
     * @param paramJsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public Date deserialize(JsonElement paramJsonElement, Type paramType,
            JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException {
        if (null == paramJsonElement) {
            return null;
        }
        String dateStr = paramJsonElement.getAsString();
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        Date date = null;
        boolean flag = false;
        Matcher matcher = pattern.matcher(dateStr);
        if (matcher.matches()) {
            date = new Date(Long.parseLong(dateStr));
        } else {
            for (String format : parsePatterns) {
                SimpleDateFormat objSdf = new SimpleDateFormat(format);
                try {
                    date = objSdf.parse(dateStr);
                    flag = true;
                } catch (ParseException e) {
                }
                if (flag) {
                    break;
                }
            }
        }
        return date;
    }
}
