package com.cdtde.chongdetang.util.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/12 21:46
 * @Version 1
 */
public class DateFormatAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(
                new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(src)
        );
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(json.getAsString());
        } catch (ParseException e) {
            return null;
        }
    }
}
